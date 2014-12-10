package com.magnoliales.annotatedapp.actions;

import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.framework.action.DeleteItemActionDefinition;

public class DeleteActionBuilder extends AbstractActionBuilder<DeleteActionBuilder> {

    private String name;

    public DeleteActionBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ActionDefinition definition() {
        DeleteItemActionDefinition actionDefinition = new DeleteItemActionDefinition();
        actionDefinition.setName(name);
        actionDefinition.setAvailability(getAvailability());
        actionDefinition.setIcon(getIcon("icon-delete"));
        return actionDefinition;
    }
}
