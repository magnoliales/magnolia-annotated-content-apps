package com.magnoliales.annotatedapp.actions;

import com.magnoliales.annotatedapp.dialog.AnnotatedFormDialogDefinitionProvider;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.dialog.registry.DialogDefinitionRegistry;
import info.magnolia.ui.framework.action.OpenCreateDialogActionDefinition;

public class AddActionBuilder extends AbstractActionBuilder<AddActionBuilder> {

    protected String name;
    protected String appName;
    protected String nodeType;
    protected Class<?> nodeClass;

    public AddActionBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public AddActionBuilder setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public AddActionBuilder setNodeType(String nodeType) {
        this.nodeType = nodeType;
        return this;
    }

    public AddActionBuilder setFormDialogNodeClass(Class<?> nodeClass) {
        this.nodeClass = nodeClass;
        return this;
    }

    @Override
    public ActionDefinition definition() {

        String id = appName + ":" + name;

        Components.getComponent(DialogDefinitionRegistry.class).register(
                new AnnotatedFormDialogDefinitionProvider(id, nodeClass)
        );

        OpenCreateDialogActionDefinition definition = new OpenCreateDialogActionDefinition();
        definition.setName(name);
        definition.setDialogName(id);
        definition.setNodeType(nodeType);
        definition.setIcon(icon == null ? "icon-add-node-content" : icon);
        definition.setAvailability(availability);
        return definition;
    }
}
