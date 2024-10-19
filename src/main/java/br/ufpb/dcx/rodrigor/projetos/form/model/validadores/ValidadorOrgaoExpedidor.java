package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorOrgaoExpedidor implements ValidadorCampo {
    public ResultadoValidacao validarCampo(String valor) {
        if (valor == null || valor.isEmpty()) {
            return new ResultadoValidacao("O órgão expedidor não pode ser vazio");
        }
        if (!valor.matches("^[A-Z]{2,10}$")) {
            return new ResultadoValidacao("Informe um órgão expedidor válido (somente letras maiúsculas)");
        }
        return new ResultadoValidacao();
    }
}
