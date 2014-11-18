package com.magnoliales.annotatedapp.dialog;

import info.magnolia.registry.RegistrationException;
import info.magnolia.ui.dialog.definition.FormDialogDefinition;
import info.magnolia.ui.dialog.formdialog.FormDialogPresenter;
import info.magnolia.ui.dialog.formdialog.FormDialogPresenterImpl;
import info.magnolia.ui.dialog.registry.DialogDefinitionProvider;

public class AnnotatedFormDialogDefinitionProvider implements DialogDefinitionProvider {

    private final String id;
    private final FormDialogDefinition formDialogDefinition;

    public AnnotatedFormDialogDefinitionProvider(String id, FormDialogDefinition formDialogDefinition) {
        this.id = id;
        this.formDialogDefinition = formDialogDefinition;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public FormDialogDefinition getDialogDefinition() {
        return formDialogDefinition;
    }

    @Override
    public Class<? extends FormDialogPresenter> getPresenterClass() throws RegistrationException {
        return FormDialogPresenterImpl.class;
    }
}
