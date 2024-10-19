package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorCep implements ValidadorCampo {
    public ResultadoValidacao validarCampo(String valor) {
        if (valor == null || valor.isEmpty()) {
            return new ResultadoValidacao("O CEP não pode ser vazio");
        }
        if (!valor.matches("^\\d{5}-\\d{3}$")) {
            return new ResultadoValidacao("Informe um CEP válido no formato XXXXX-XXX");
        }
        return new ResultadoValidacao();
    }
}
