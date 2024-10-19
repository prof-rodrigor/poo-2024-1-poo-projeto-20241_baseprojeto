package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorEstado implements ValidadorCampo {
    public ResultadoValidacao validarCampo(String valor) {
        if (valor == null || valor.isEmpty()) {
            return new ResultadoValidacao("O estado não pode ser vazio");
        }
        if (!valor.matches("^[A-Z]{2}$")) { // Exemplo: SP, RJ, MG
            return new ResultadoValidacao("Informe um estado válido com 2 letras");
        }
        return new ResultadoValidacao();
    }

}
