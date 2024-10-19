package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorNumero implements ValidadorCampo {
    public ResultadoValidacao validarCampo(String valor) {
        if (valor == null || valor.isEmpty()) {
            return new ResultadoValidacao("O número não pode ser vazio");
        }
        if (!valor.matches("^\\d+$")) {
            return new ResultadoValidacao("Informe um número válido");
        }
        return new ResultadoValidacao();
    }
}
