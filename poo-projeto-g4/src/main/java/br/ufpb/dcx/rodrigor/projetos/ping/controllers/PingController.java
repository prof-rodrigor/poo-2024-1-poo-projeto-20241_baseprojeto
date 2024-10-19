package br.ufpb.dcx.rodrigor.projetos.ping.controllers;

import br.ufpb.dcx.rodrigor.projetos.Keys;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class PingController {

    private static final Logger logger = LogManager.getLogger(PingController.class);

    // metodo que serve para renderiza a pagina de ping com informacoes sobre o serviço
    public void mostrarPaginaPing(Context ctx) {
        String pingHost = ctx.appData(Keys.SERVICO_PING_HOST.key());
        String nomeServico = recuperarNomeServico(pingHost);

        ctx.attribute("servico_host", pingHost);
        ctx.attribute("servico_nome", nomeServico);
        ctx.render("/ping/ping.html");
    }

    private String recuperarNomeServico(String pingHost) {
        if (pingHost == null || pingHost.isEmpty()) {
            logger.warn("Host do serviço de ping não configurado.");
            return "Desconhecido";
        }

        String urlServico = pingHost;
        try {
            HttpURLConnection conexao = criarConexao(urlServico);

            if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return extrairNomeServicoDaResposta(conexao);
            } else {
                logger.warn("Resposta inesperada do serviço: HTTP " + conexao.getResponseCode());
                return "Desconhecido";
            }
        } catch (IOException e) {
            logger.error("Erro ao recuperar nome do serviço", e);
            return "Desconhecido";
        }
    }

    private HttpURLConnection criarConexao(String urlServico) throws IOException {
        URL url = new URL(urlServico);
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        conexao.setRequestProperty("Accept", "application/json");
        return conexao;
    }

    private String extrairNomeServicoDaResposta(HttpURLConnection conexao) throws IOException {
        try (InputStream fluxoResposta = conexao.getInputStream()) {
            String respostaJson = new String(fluxoResposta.readAllBytes(), StandardCharsets.UTF_8);
            Map<String, String> mapaResposta = new ObjectMapper().readValue(respostaJson, Map.class);
            return mapaResposta.getOrDefault("servico.nome", "Desconhecido");
        }
    }

    public void ping(Context ctx) {
        String serviceName = ctx.appData(Keys.SERVICO_NOME.key());
        ctx.json(Map.of("servico.nome", serviceName));
    }
}
