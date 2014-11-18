package com.magnoliales.annotatedapp.actions;

import com.magnoliales.annotatedapp.dialog.AnnotatedFormDialogDefinitionProvider;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.dialog.definition.FormDialogDefinition;
import info.magnolia.ui.dialog.registry.DialogDefinitionRegistry;
import info.magnolia.ui.framework.action.OpenEditDialogActionDefinition;

public class EditActionBuilder extends AbstractActionBuilder<EditActionBuilder> {

    protected String name;
    protected String appName;
    protected FormDialogDefinition formDialogDefinition;

    public EditActionBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public EditActionBuilder setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public EditActionBuilder setFormDialogDefinition(FormDialogDefinition formDialogDefinition) {
        this.formDialogDefinition = formDialogDefinition;
        return this;
    }

    @Override
    public ActionDefinition definition() {

        String id = appName + ":" + name;

        Components.getComponent(DialogDefinitionRegistry.class).register(
                new AnnotatedFormDialogDefinitionProvider(id, formDialogDefinition));

        OpenEditDialogActionDefinition definition = new OpenEditDialogActionDefinition();
        definition.setName(name);
        definition.setDialogName(id);
        definition.setIcon(icon == null ? "icon-edit" : icon);
        definition.setAvailability(availability);
        return definition;
    }
}
