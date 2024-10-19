package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorCPF implements ValidadorCampo {
    public ResultadoValidacao validarCampo(String valor) {
        if (valor == null || valor.isEmpty()) {
            return new ResultadoValidacao("O CPF não pode ser vazio");
        }
        if (!valor.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")) {
            return new ResultadoValidacao("Informe um CPF válido no formato XXX.XXX.XXX-XX");
        }
        return new ResultadoValidacao();
    }
}
