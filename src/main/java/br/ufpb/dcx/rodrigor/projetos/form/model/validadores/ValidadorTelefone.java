package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorTelefone implements ValidadorCampo {
    public ResultadoValidacao validarCampo(String valor) {
        if (valor == null || valor.isEmpty()) {
            return new ResultadoValidacao("O telefone não pode ser vazio");
        }
        if (!valor.matches("^\\(\\d{2}\\) \\d{4,5}-\\d{4}$")) {
            return new ResultadoValidacao("Informe um telefone válido no formato (XX) XXXXX-XXXX");
        }
        return new ResultadoValidacao();
    }
}
