package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorCidade implements ValidadorCampo {
    public ResultadoValidacao validarCampo(String valor) {
        if (valor == null || valor.isEmpty()) {
            return new ResultadoValidacao("A cidade não pode ser vazia");
        }
        if (!valor.matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
            return new ResultadoValidacao("Informe uma cidade válida");
        }
        return new ResultadoValidacao();
    }
}
