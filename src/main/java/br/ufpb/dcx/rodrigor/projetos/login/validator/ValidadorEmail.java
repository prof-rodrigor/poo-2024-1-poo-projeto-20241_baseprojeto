package br.ufpb.dcx.rodrigor.projetos.login.validator;

import br.ufpb.dcx.rodrigor.projetos.login.interfaces.Validador;

public class ValidadorEmail implements Validador {
    @Override
    public boolean validar(String email) {
        return email != null && email.contains("@") && email.indexOf("@") < email.lastIndexOf(".") && email.length() <= 64;
    }
}
