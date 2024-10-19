package br.ufpb.dcx.rodrigor.projetos.form.controller;

import br.ufpb.dcx.rodrigor.projetos.Keys;
import br.ufpb.dcx.rodrigor.projetos.form.model.Formulario;
import br.ufpb.dcx.rodrigor.projetos.form.model.ResultadoValidacao;
import br.ufpb.dcx.rodrigor.projetos.form.services.FormService;
import io.javalin.http.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class FormController {

    private static final Logger logger = LogManager.getLogger(FormController.class);

    private final FormService formService;

    // Construtor que recebe o FormService
    public FormController(FormService formService) {
        this.formService = formService;
    }

    public void abrirFormulario(Context ctx) {
        String formId = ctx.pathParam("formId");
        Formulario form = formService.getFormulario(formId);
        if (form == null) {
            ctx.status(404).result("Formulário não encontrado");
            return;
        }
        ctx.attribute("form", form);
        ctx.render("/forms/formulario.html");
    }

    public void validarFormulario(@NotNull Context context) {
        String formId = context.pathParam("formId");
        logger.debug("formId: {}", formId);
        Formulario form = formService.getFormulario(formId);
        logger.debug("form: {}", form);
        if (form == null) {
            context.status(404).result("Formulário não encontrado");
            return;
        }

        Map<String, ResultadoValidacao> erros = new LinkedHashMap<>();
        form.getCampos().forEach(campo -> {
            String valor = context.formParam(campo.getId());
            if (campo.isObrigatorio() && (valor == null || valor.trim().isEmpty())) {
                erros.put(campo.getId(), new ResultadoValidacao("Campo obrigatório"));
            } else {
                campo.setValor(valor);
                ResultadoValidacao resultado = campo.validar();
                if (!resultado.ok()) {
                    erros.put(campo.getId(), resultado);
                }
            }
        });

        if (erros.isEmpty()) {
            context.render("/forms/sucesso.html");
        } else {
            context.attribute("form", form);
            context.attribute("erros", erros);
            context.render("/forms/formulario.html");
        }
    }
}
