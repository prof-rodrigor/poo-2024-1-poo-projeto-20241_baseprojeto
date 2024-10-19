package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorRG implements ValidadorCampo {
    public ResultadoValidacao validarCampo(String valor) {
        if (valor == null || valor.isEmpty()) {
            return new ResultadoValidacao("O RG não pode ser vazio");
        }
        if (!valor.matches("^\\d{2}\\.\\d{3}\\.\\d{3}-\\d{1}$")) { // Exemplo: 12.345.678-9
            return new ResultadoValidacao("Informe um RG válido no formato XX.XXX.XXX-X");
        }
        return new ResultadoValidacao();
    }
}
