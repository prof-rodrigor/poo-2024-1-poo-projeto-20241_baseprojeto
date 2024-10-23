package br.ufpb.dcx.rodrigor.projetos.login.validator;

import br.ufpb.dcx.rodrigor.projetos.login.interfaces.Validador;

public abstract class ValidadorDecorator implements Validador {
    protected Validador validador;
    public ValidadorDecorator(Validador validador){
        this.validador = validador;
    }

    @Override
    public boolean validar(String valor) {
        return validador.validar(valor);
    }
}
