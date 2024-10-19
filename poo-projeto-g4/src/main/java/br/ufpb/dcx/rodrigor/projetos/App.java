package br.ufpb.dcx.rodrigor.projetos;

import br.ufpb.dcx.rodrigor.projetos.db.MongoDBConnector;
import br.ufpb.dcx.rodrigor.projetos.form.controller.FormController;
import br.ufpb.dcx.rodrigor.projetos.form.services.FormService;
import br.ufpb.dcx.rodrigor.projetos.login.LoginController;
import br.ufpb.dcx.rodrigor.projetos.participante.controllers.ParticipanteController;
import br.ufpb.dcx.rodrigor.projetos.participante.services.ParticipanteService;
import br.ufpb.dcx.rodrigor.projetos.ping.controllers.PingController;
import br.ufpb.dcx.rodrigor.projetos.projeto.controllers.ProjetoController;
import br.ufpb.dcx.rodrigor.projetos.projeto.services.ProjetoService;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinThymeleaf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.function.Consumer;

public class App {
    private static final Logger logger = LogManager.getLogger();

    private static final int PORTA_PADRAO = 8000;
    private static final String PROP_PORTA_SERVIDOR = "porta.servidor";
    private static final String PROP_MONGODB_CONNECTION_STRING = "mongodb.connectionString";
    private static final String SERVICO_NOME = "servico.nome";

    private final Properties propriedades;
    private MongoDBConnector mongoDBConnector = null;

    public App() {
        this.propriedades = carregarPropriedades();
    }

    public void iniciar() {
        Javalin app = inicializarJavalin();
        configurarPaginasDeErro(app);
        configurarRotas(app);

        // Lidando com exceções não tratadas
        app.exception(Exception.class, (e, ctx) -> {
            logger.error("Erro não tratado", e);
            ctx.status(500);
        });
    }

    private void registrarServicos(JavalinConfig config, MongoDBConnector mongoDBConnector) {
        // SERVICE NOME
        String nomeServico = propriedades.getProperty(SERVICO_NOME);
        if (nomeServico == null) {
            logger.error("Defina a propriedade '{}' no arquivo propriedades", SERVICO_NOME);
            System.exit(1);
        }
        config.appData(Keys.SERVICO_NOME.key(), nomeServico);

        // SERVICO HOST PING
        String hostPing = propriedades.getProperty("servico.ping.host");
        if (hostPing == null) {
            logger.error("Defina o host do serviço ping no arquivo de propriedades.");
            logger.error("exemplo: 'servico.ping.host=https://localhost:8004'");
            System.exit(1);
        }
        config.appData(Keys.SERVICO_PING_HOST.key(), hostPing);

        ParticipanteService participanteService = new ParticipanteService(mongoDBConnector);
        FormService formService = new FormService(); // Instanciando o FormService

        config.appData(Keys.PROJETO_SERVICE.key(), new ProjetoService(mongoDBConnector, participanteService));
        config.appData(Keys.PARTICIPANTE_SERVICE.key(), participanteService);
        config.appData(Keys.FORM_SERVICE.key(), formService); // Registrando o FormService
    }

    private void configurarPaginasDeErro(Javalin app) {
        app.error(404, ctx -> ctx.render("erro_404.html"));
        app.error(500, ctx -> ctx.render("erro_500.html"));
    }

    private Javalin inicializarJavalin() {
        int porta = obterPortaServidor();

        logger.info("Iniciando aplicação na porta {}", porta);

        Consumer<JavalinConfig> configConsumer = this::configureJavalin;

        return Javalin.create(configConsumer).start(porta);
    }

    private void configureJavalin(JavalinConfig config) {
        TemplateEngine templateEngine = configurarThymeleaf();

        config.events(event -> {
            event.serverStarting(() -> {
                mongoDBConnector = inicializarMongoDB();
                config.appData(Keys.MONGO_DB.key(), mongoDBConnector);
                registrarServicos(config, mongoDBConnector);
            });
            event.serverStopping(() -> {
                if (mongoDBConnector == null) {
                    logger.error("MongoDBConnector não deveria ser nulo ao parar o servidor");
                } else {
                    mongoDBConnector.close();
                    logger.info("Conexão com o MongoDB encerrada com sucesso");
                }
            });
        });
        config.staticFiles.add(staticFileConfig -> {
            staticFileConfig.directory = "/public";
            staticFileConfig.location = Location.CLASSPATH;
        });
        config.fileRenderer(new JavalinThymeleaf(templateEngine));
    }

    private int obterPortaServidor() {
        if (propriedades.containsKey(PROP_PORTA_SERVIDOR)) {
            try {
                return Integer.parseInt(propriedades.getProperty(PROP_PORTA_SERVIDOR));
            } catch (NumberFormatException e) {
                logger.error("Porta definida no arquivo de propriedades não é um número válido: '{}'", propriedades.getProperty(PROP_PORTA_SERVIDOR));
                System.exit(1);
            }
        } else {
            logger.info("Porta não definida no arquivo de propriedades, utilizando porta padrão {}", PORTA_PADRAO);
        }
        return PORTA_PADRAO;
    }

    private TemplateEngine configurarThymeleaf() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    private MongoDBConnector inicializarMongoDB() {
        String connectionString = propriedades.getProperty(PROP_MONGODB_CONNECTION_STRING);
        logger.info("Lendo string de conexão ao MongoDB a partir do application.properties");
        if (connectionString == null) {
            logger.error("O string de conexão ao MongoDB não foi definido no arquivo /src/main/resources/application.properties");
            logger.error("Defina a propriedade '{}' no arquivo de propriedades", PROP_MONGODB_CONNECTION_STRING);
            System.exit(1);
        }

        logger.info("Conectando ao MongoDB");
        MongoDBConnector db = new MongoDBConnector(connectionString);
        if (db.conectado("config")) {
            logger.info("Conexão com o MongoDB estabelecida com sucesso");
        } else {
            logger.error("Falha ao conectar ao MongoDB");
            System.exit(1);
        }
        return db;
    }

    private void configurarRotas(Javalin app) {
        // tentativa 57
        FormService formService = new FormService(); // Instância direta

        FormController formController = new FormController(formService);

        // Rotas de login e logout
        LoginController loginController = new LoginController();
        app.get("/", ctx -> ctx.redirect("/login"));
        app.get("/login", loginController::mostrarPaginaLogin);
        app.post("/login", loginController::processarLogin);
        app.get("/logout", loginController::logout);

        app.get("/area-interna", ctx -> {
            if (ctx.sessionAttribute("usuario") == null) {
                ctx.redirect("/login");
            } else {
                ctx.render("area_interna.html");
            }
        });

        // Rotas do formulário
        app.get("/formularios/{formId}", formController::abrirFormulario);
        app.post("/formularios/{formId}/validar", formController::validarFormulario);

        // Outros controles
        ProjetoController projetoController = new ProjetoController();
        app.get("/projetos", projetoController::listarProjetos);
        app.get("/projetos/novo", projetoController::mostrarFormulario);
        app.post("/projetos", projetoController::adicionarProjeto);
        app.get("/projetos/{id}/remover", projetoController::removerProjeto);

        ParticipanteController participanteController = new ParticipanteController();
        app.get("/participantes", participanteController::listarParticipantes);
        app.get("/participantes/novo", participanteController::mostrarFormularioCadastro);
        app.post("/participantes", participanteController::adicionarParticipante);
        app.get("/participantes/{id}/remover", participanteController::removerParticipante);
        app.get("/v1/participantes", participanteController::participantesPorCategoria);
        app.get("/v1/participantes/{id}", participanteController::participantePorId);

        PingController pingController = new PingController();
        app.get("/v1/ping", pingController::ping);
        app.get("/ping", pingController::mostrarPaginaPing);
    }
    private Properties carregarPropriedades() {
        Properties prop = new Properties();
        try (InputStream input = App.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                logger.error("Arquivo de propriedades /src/main/resources/application.properties não encontrado");
                logger.error("Use o arquivo application.properties.exemplo como base para criar o arquivo application.properties");
                System.exit(1);
            }
            prop.load(input);
        } catch (IOException ex) {
            logger.error("Erro ao carregar o arquivo de propriedades /src/main/resources/application.properties", ex);
            System.exit(1);
        }
        return prop;
    }

    public static void main(String[] args) {
        try {
            new App().iniciar();
        } catch (Exception e) {
            logger.error("Erro ao iniciar a aplicação", e);
            System.exit(1);
        }
    }
}
