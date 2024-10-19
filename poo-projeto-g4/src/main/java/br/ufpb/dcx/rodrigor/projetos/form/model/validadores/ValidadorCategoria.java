package br.ufpb.dcx.rodrigor.projetos.form.model.validadores;

import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.model.ValidadorCampo;

public class ValidadorCategoria implements ValidadorCampo {
    public ResultadoValidacao validarCampo(String valor) {
        if (valor == null || valor.isEmpty()) {
            return new ResultadoValidacao("A categoria não pode ser vazia");
        }
        // Exemplo de categorias: "Estudante", "Professor", "Profissional"
        if (!valor.matches("^(Estudante|Professor|Profissional)$")) {
            return new ResultadoValidacao("Informe uma categoria válida");
        }
        return new ResultadoValidacao();
    }
}
