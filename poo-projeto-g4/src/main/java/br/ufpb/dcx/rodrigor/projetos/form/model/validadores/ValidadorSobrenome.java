package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorSobrenome implements ValidadorCampo {
    public ResultadoValidacao validarCampo(String valor) {
        if (valor == null || valor.isEmpty()) {
            return new ResultadoValidacao("O sobrenome não pode ser vazio");
        }
        if (!valor.matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
            return new ResultadoValidacao("Informe um sobrenome válido");
        }
        return new ResultadoValidacao();
    }

}
