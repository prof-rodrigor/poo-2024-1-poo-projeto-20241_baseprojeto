package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorPeriodoEntrada implements ValidadorCampo {
    public ResultadoValidacao validarCampo(String valor) {
        if (valor == null || valor.isEmpty()) {
            return new ResultadoValidacao("O período de entrada não pode ser vazio");
        }
        if (!valor.matches("^\\d{4}\\.\\d{1}$")) { // Exemplo: 2023.1
            return new ResultadoValidacao("Informe o período de entrada no formato aaaa.n");
        }
        return new ResultadoValidacao();
    }
}
