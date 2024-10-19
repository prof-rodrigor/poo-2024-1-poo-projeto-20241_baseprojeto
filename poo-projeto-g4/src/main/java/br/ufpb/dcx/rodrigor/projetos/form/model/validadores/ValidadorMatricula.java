package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorMatricula implements ValidadorCampo {
    public ResultadoValidacao validarCampo(String valor) {
        if (valor == null || valor.isEmpty()) {
            return new ResultadoValidacao("A matrícula não pode ser vazia");
        }
        if (!valor.matches("^\\d{8}$")) { // Exemplo: 8 dígitos numéricos
            return new ResultadoValidacao("Informe uma matrícula válida com 8 dígitos");
        }
        return new ResultadoValidacao();
    }

}
