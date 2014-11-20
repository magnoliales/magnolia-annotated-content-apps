package com.magnoliales.annotatedapp.dialog;

import info.magnolia.registry.RegistrationException;
import info.magnolia.ui.dialog.definition.ConfiguredFormDialogDefinition;
import info.magnolia.ui.dialog.definition.FormDialogDefinition;
import info.magnolia.ui.dialog.formdialog.FormDialogPresenter;
import info.magnolia.ui.dialog.formdialog.FormDialogPresenterImpl;
import info.magnolia.ui.dialog.registry.DialogDefinitionProvider;

public class AnnotatedFormDialogDefinitionProvider implements DialogDefinitionProvider {

    private final String id;
    private final Class<?> nodeClass;

    public AnnotatedFormDialogDefinitionProvider(String id, Class<?> nodeClass) {
        this.id = id;
        this.nodeClass = nodeClass;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public FormDialogDefinition getDialogDefinition() {
        return AnnotatedFormDialogDefinitionFactory.INSTANCE.createFormDialogDefinition(nodeClass);
    }

    @Override
    public Class<? extends FormDialogPresenter> getPresenterClass() throws RegistrationException {
        return FormDialogPresenterImpl.class;
    }
}
