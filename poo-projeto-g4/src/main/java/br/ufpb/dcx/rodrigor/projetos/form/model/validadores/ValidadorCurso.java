package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorCurso implements ValidadorCampo {
    public ResultadoValidacao validarCampo(String valor) {
        if (valor == null || valor.isEmpty()) {
            return new ResultadoValidacao("O curso não pode ser vazio");
        }
        if (!valor.matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
            return new ResultadoValidacao("Informe um curso válido");
        }
        return new ResultadoValidacao();
    }
}
