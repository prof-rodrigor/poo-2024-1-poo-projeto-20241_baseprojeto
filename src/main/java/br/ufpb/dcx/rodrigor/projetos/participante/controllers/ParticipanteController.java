package br.ufpb.dcx.rodrigor.projetos.participante.controllers;

import br.ufpb.dcx.rodrigor.projetos.Keys;
import br.ufpb.dcx.rodrigor.projetos.participante.model.CategoriaParticipante;
import br.ufpb.dcx.rodrigor.projetos.participante.model.Participante;
import br.ufpb.dcx.rodrigor.projetos.participante.services.ParticipanteService;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import org.bson.types.ObjectId;
import io.javalin.http.BadRequestResponse;

public class ParticipanteController {

    public ParticipanteController() {
    }

    public void listarParticipantes(Context ctx) {
        // não permite que o usuario possa acessar os participantes antes de fazer o login
        if (ctx.sessionAttribute("usuario") == null) {
            ctx.redirect("/login");
        } else {
            ParticipanteService participanteService = ctx.appData(Keys.PARTICIPANTE_SERVICE.key());
            ctx.attribute("participantes", participanteService.listarParticipantes());
            ctx.render("/participantes/lista_participantes.html");
        }
    }

    public void mostrarFormularioCadastro(Context ctx) {
        ctx.attribute("categorias", CategoriaParticipante.values());
        ctx.render("/participantes/formulario_participante.html");
    }


        public void adicionarParticipante(Context ctx) {
            ParticipanteService participanteService = ctx.appData(Keys.PARTICIPANTE_SERVICE.key());
            Participante participante = new Participante();

            // string pra validar o nao uso dos caracteres
            String caracteresIndesejados = "^[\\p{L}0-9 ]+$";
            String caracteresPermitidosParaNome = "^[\\p{L}0-9 ~´ç]+$";

            String nome = ctx.formParam("nome");
            if (nome == null || nome.trim().isEmpty() || !nome.matches(caracteresPermitidosParaNome)) {
                throw new BadRequestResponse("O nome não pode ser vazio e deve conter apenas caracteres válidos.");
            }

            // Validação de Sobrenome
            String sobrenome = ctx.formParam("sobrenome");
            if (sobrenome == null || sobrenome.trim().isEmpty() || !sobrenome.matches(caracteresPermitidosParaNome)) {
                throw new BadRequestResponse("O sobrenome não pode ser vazio e deve conter apenas caracteres válidos.");
            }

            // Validação de Email
            String email = ctx.formParam("email");
            if (email == null || email.trim().isEmpty() || !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                throw new BadRequestResponse("O e-mail fornecido não é válido.");
            }

            // Validação de Telefone
            String telefone = ctx.formParam("telefone");
            if (telefone == null || telefone.trim().isEmpty() || !telefone.matches("^\\+?[\\d\\s\\-\\(\\)]{9,20}$")) {
                throw new BadRequestResponse("O telefone fornecido não é válido. Use um número com entre 9 e 20 dígitos.");
            }

            // Validação de Matrícula
            String matricula = ctx.formParam("matricula");
            if (matricula == null || matricula.trim().isEmpty() || !matricula.matches("\\d{11}")) {
                throw new BadRequestResponse("A matrícula deve conter 11 dígitos e não pode estar vazia.");
            }

            // Validação de CPF
            String cpf = ctx.formParam("cpf");
            if (cpf == null || cpf.trim().isEmpty() || !validarCPF(cpf)) {
                throw new BadRequestResponse("O CPF é inválido.");
            }

            // Validação de RG
            String rg = ctx.formParam("rg");
            if (rg == null || rg.trim().isEmpty() || !rg.matches("\\d{5,12}")) {
                throw new BadRequestResponse("O RG deve conter entre 5 e 12 dígitos e não pode estar vazio.");
            }

            // Validação de Sexo
            String sexo = ctx.formParam("sexo");
            if (sexo == null || sexo.trim().isEmpty()) {
                throw new BadRequestResponse("O sexo deve ser M, F ou Outro e não pode estar vazio.");
            }

            // Validação de Curso
            String curso = ctx.formParam("curso");
            if (curso == null || curso.trim().isEmpty()) {
                throw new BadRequestResponse("O curso não pode ser vazio e não deve conter caracteres especiais.");
            }

            // Validação de Período de Entrada
            String periodoDeEntrada = ctx.formParam("periodoDeEntrada");
            if (periodoDeEntrada == null || periodoDeEntrada.trim().isEmpty()) {
                throw new BadRequestResponse("O período de entrada não pode estar vazio.");
            }

            // Validação de Orgao Expedidor
            String orgaoExpedidor = ctx.formParam("orgaoExpedidor");
            if (orgaoExpedidor == null || orgaoExpedidor.trim().isEmpty() || !orgaoExpedidor.matches(caracteresIndesejados)) {
                throw new BadRequestResponse("O órgão expedidor não pode ser vazio e não deve conter caracteres especiais.");
            }

            // Validação de Instagram
            String instagram = ctx.formParam("instagram");
            if (instagram == null || instagram.trim().isEmpty() || !instagram.matches("^https?://(www\\.)?instagram\\.com/.*$")) {
                throw new BadRequestResponse("O Instagram deve ter um link válido.");
            }

            // Validação de LinkedIn
            String linkedIn = ctx.formParam("linkedIn");
            if (linkedIn == null || linkedIn.trim().isEmpty() || !linkedIn.matches("^https?://(www\\.)?linkedin\\.com/.*$")) {
                throw new BadRequestResponse("O LinkedIn deve ter um link válido.");
            }

            // Validação de GitHub
            String github = ctx.formParam("github");
            if (github == null || github.trim().isEmpty() || !github.matches("^https?://(www\\.)?github\\.com/.*$")) {
                throw new BadRequestResponse("O GitHub deve ter um link válido.");
            }

            // Validação de Pais
            String pais = ctx.formParam("pais");
            if (pais == null || pais.trim().isEmpty() || !pais.matches(caracteresIndesejados)) {
                throw new BadRequestResponse("O país não pode ser vazio e não deve conter caracteres especiais.");
            }

            // Validação de Estado
            String estado = ctx.formParam("estado");
            if (estado == null || estado.trim().isEmpty() || !estado.matches(caracteresIndesejados)) {
                throw new BadRequestResponse("O estado não pode ser vazio e não deve conter caracteres especiais.");
            }

            // Validação de Cidade
            String cidade = ctx.formParam("cidade");
            if (cidade == null || cidade.trim().isEmpty() || !cidade.matches(caracteresIndesejados)) {
                throw new BadRequestResponse("A cidade não pode ser vazia e não deve conter caracteres especiais.");
            }

            // Validação de CEP
            String cep = ctx.formParam("cep");
            if (cep == null || cep.trim().isEmpty() || !cep.matches("\\d{5}-\\d{3}")) {
                throw new BadRequestResponse("O CEP deve seguir o formato XXXXX-XXX e não pode estar vazio.");
            }

            // Validação de Bairro
            String bairro = ctx.formParam("bairro");
            if (bairro == null || bairro.trim().isEmpty() || !bairro.matches(caracteresIndesejados)) {
                throw new BadRequestResponse("O bairro não pode ser vazio e não deve conter caracteres especiais.");
            }

            // Validação de Rua
            String rua = ctx.formParam("rua");
            if (rua == null || rua.trim().isEmpty() || !rua.matches(caracteresIndesejados)) {
                throw new BadRequestResponse("A rua não pode ser vazia e não deve conter caracteres especiais.");
            }

            // Validação de Número
            String numero = ctx.formParam("numero");
            if (numero == null || numero.trim().isEmpty() || !numero.matches("\\d+")) {
                throw new BadRequestResponse("O número deve ser numérico e não pode estar vazio.");
            }

            // Setando os atributos validados
            participante.setId(new ObjectId()); // Gera um novo ObjectId para o participante
            participante.setNome(nome);
            participante.setSobrenome(sobrenome);
            participante.setEmail(email);
            participante.setTelefone(telefone);
            participante.setMatricula(matricula);
            participante.setCurso(curso);
            participante.setPeriodoDeEntrada(periodoDeEntrada);
            participante.setCpf(cpf);
            participante.setRg(rg);
            participante.setOrgaoExpedidor(orgaoExpedidor);
            participante.setSexo(sexo);
            participante.setInstagram(instagram);
            participante.setLinkedIn(linkedIn);
            participante.setGithub(github);
            participante.setPais(pais);
            participante.setEstado(estado);
            participante.setCidade(cidade);
            participante.setCEP(cep);
            participante.setBairro(bairro);
            participante.setRua(rua);
            participante.setNumero(numero);

            // Validação da categoria
            String categoriaStr = ctx.formParam("categoria");
            try {
                participante.setCategoria(CategoriaParticipante.valueOf(categoriaStr));
            } catch (IllegalArgumentException | NullPointerException e) {
                throw new BadRequestResponse("Categoria inválida e não pode ser vazia.");
            }

            participanteService.adicionarParticipante(participante);
            ctx.redirect("/participantes");
        }

        // Função para vaslidar o cpf
        private boolean validarCPF(String cpf) {
            cpf = cpf.replaceAll("\\D", ""); // Remove qualquer coisa que nao seja numero

            if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
                return false; // CPF inválido se todos os dígitos forem iguais
            }

            // Calculo do primeiro dígito
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int primeiroDigitoVerificador = 11 - (soma % 11);
            if (primeiroDigitoVerificador >= 10) {
                primeiroDigitoVerificador = 0;
            }

            // Verifica se o primeiro dígito esta certo
            if (primeiroDigitoVerificador != Character.getNumericValue(cpf.charAt(9))) {
                return false;
            }

            // Calculo do segundo dígito
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int segundoDigitoVerificador = 11 - (soma % 11);
            if (segundoDigitoVerificador >= 10) {
                segundoDigitoVerificador = 0;
            }

            // Verifica se o segundo dígito esta certo
            return segundoDigitoVerificador == Character.getNumericValue(cpf.charAt(10));
        }


    public void removerParticipante(Context ctx) {
        ParticipanteService participanteService = ctx.appData(Keys.PARTICIPANTE_SERVICE.key());
        String id = ctx.pathParam("id");
        participanteService.removerParticipante(id);
        ctx.redirect("/participantes");
    }

    // participantes em json
    public void participantesPorCategoria(Context ctx) {
        ParticipanteService participanteService = ctx.appData(Keys.PARTICIPANTE_SERVICE.key());
        String categoriaParam = ctx.queryParam("categoria");

        if (categoriaParam != null) {
            try {
                CategoriaParticipante categoria = CategoriaParticipante.valueOf(categoriaParam.toUpperCase());
                var participantes = participanteService.listarParticipantesPorCategoria(categoria);
                if (participantes.isEmpty()) {
                    ctx.status(HttpStatus.NOT_FOUND).result("Nenhum participante encontrado para a categoria: " + categoriaParam);
                } else {
                    ctx.json(participantes);
                }
            } catch (IllegalArgumentException e) {
                ctx.status(HttpStatus.BAD_REQUEST).result("Categoria inválida: " + categoriaParam);
            }
        } else {
            ctx.json(participanteService.listarParticipantes());
        }
    }

    // v1/participantes/<id>
    public void participantePorId(Context ctx) {
        ParticipanteService participanteService = ctx.appData(Keys.PARTICIPANTE_SERVICE.key());
        String id = ctx.pathParam("id");
        var participante = participanteService.buscarParticipantePorId(id);
        if (participante.isPresent()) {
            ctx.json(participante.get());
        } else {
            ctx.status(HttpStatus.NOT_FOUND)
                    .contentType("application/json")
                    .result("{\"message\": \"Participante não encontrado para o id: " + id + "\"}");
        }
    }

}