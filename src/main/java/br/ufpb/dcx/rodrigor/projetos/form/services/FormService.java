package br.ufpb.dcx.rodrigor.projetos.form.services;

import br.ufpb.dcx.rodrigor.projetos.form.model.Campo;
import br.ufpb.dcx.rodrigor.projetos.form.model.Formulario;
import br.ufpb.dcx.rodrigor.projetos.form.model.validadores.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class FormService {

    private final Map<String, Formulario> formularios = new LinkedHashMap<>();


    public FormService(){
        iniciarFormularios();
    }

    public Formulario getFormulario(String id){
        return formularios.get(id);
    }

    public void iniciarFormularios(){
        Formulario form = new Formulario("usuario", "Cadastro de Usuário");
        form.addCampo(new Campo("nome", "Nome", new ValidadorTexto(3, 100), true));
        form.addCampo(new Campo("email", "Email",new ValidadorEmail(), true));
        formularios.put(form.getId(), form);

        Formulario participante = new Formulario("participante", "Cadastro de Participantes");
        participante.addCampo(new Campo("nome", "Nome", new ValidadorNome(), true ));
        participante.addCampo(new Campo("sobrenome", "Sobrenome", new ValidadorSobrenome(), true));
        participante.addCampo(new Campo("email", "Email", new ValidadorEmail(), true));
        participante.addCampo(new Campo("telefone", "Telefone", new ValidadorTelefone(), true));
        participante.addCampo(new Campo("categoria", "Categoria", new ValidadorCategoria(), true));
        participante.addCampo(new Campo("matricula", "Matrícula", new ValidadorMatricula(), true));
        participante.addCampo(new Campo("curso", "Curso", new ValidadorCurso(), true));
        participante.addCampo(new Campo("periodoEntrada", "Período de Entrada", new ValidadorPeriodoEntrada(), true));
        participante.addCampo(new Campo("cpf", "CPF", new ValidadorCPF(), true));
        participante.addCampo(new Campo("rg", "RG", new ValidadorRG(), true));
        participante.addCampo(new Campo("orgaoExpedidor", "Órgão Expedidor", new ValidadorOrgaoExpedidor(), true));
        participante.addCampo(new Campo("sexo", "Sexo", new ValidadorSexo(), true, ""));
        participante.addCampo(new Campo("linkedin", "LinkedIn", new ValidadorLinkedin(), false));
        participante.addCampo(new Campo("github", "GitHub", new ValidadorGitHub(), false));
        participante.addCampo(new Campo("endereco", "Endereço", new ValidadorEndereco(), true));
        participante.addCampo(new Campo("estado", "Estado", new ValidadorEstado(), true));
        participante.addCampo(new Campo("cidade", "Cidade", new ValidadorCidade(), true));
        participante.addCampo(new Campo("cep", "CEP", new ValidadorCep(), true));
        participante.addCampo(new Campo("bairro", "Bairro", new ValidadorBairro(), true));
        participante.addCampo(new Campo("numero", "Número", new ValidadorNumero(), true));
        formularios.put(participante.getId(), participante);
    }
}
