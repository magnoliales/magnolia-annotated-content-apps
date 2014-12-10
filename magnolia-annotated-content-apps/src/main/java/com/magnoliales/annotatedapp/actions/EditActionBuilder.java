package com.magnoliales.annotatedapp.actions;

import com.magnoliales.annotatedapp.dialog.AnnotatedFormDialogDefinitionProvider;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.dialog.registry.DialogDefinitionRegistry;
import info.magnolia.ui.framework.action.OpenEditDialogActionDefinition;

public class EditActionBuilder extends AbstractActionBuilder<EditActionBuilder> {

    private String name;
    private String appName;
    private Class<?> nodeClass;

    public EditActionBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public EditActionBuilder setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public EditActionBuilder setFormDialogNodeClass(Class<?> nodeClass) {
        this.nodeClass = nodeClass;
        return this;
    }

    @Override
    public ActionDefinition definition() {
        String id = appName + ":" + name;
        Components.getComponent(DialogDefinitionRegistry.class).register(
                new AnnotatedFormDialogDefinitionProvider(id, nodeClass)
        );
        OpenEditDialogActionDefinition definition = new OpenEditDialogActionDefinition();
        definition.setName(name);
        definition.setDialogName(id);
        definition.setIcon(getIcon("icon-edit"));
        definition.setAvailability(getAvailability());
        return definition;
    }
}
