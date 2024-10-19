package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorGitHub implements ValidadorCampo {
    public ResultadoValidacao validarCampo(String valor) {
        if (valor != null && !valor.isEmpty()) {
            if (!valor.matches("^(https?://)?(www\\.)?github\\.com/.*$")) {
                return new ResultadoValidacao("Informe um link do GitHub válido");
            }
        }
        return new ResultadoValidacao();
    }
}
