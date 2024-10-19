package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorSexo implements ValidadorCampo {
    public ResultadoValidacao validarCampo(String valor) {
        if (valor == null || valor.isEmpty()) {
            return new ResultadoValidacao("O sexo não pode ser vazio");
        }
        if (!valor.matches("^(Masculino|Feminino|Outro)$")) {
            return new ResultadoValidacao("Informe um sexo válido: Masculino, Feminino ou Outro");
        }
        return new ResultadoValidacao();
    }
}
