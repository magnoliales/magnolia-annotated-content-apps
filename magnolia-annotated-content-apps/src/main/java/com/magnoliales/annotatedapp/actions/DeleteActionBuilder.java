package com.magnoliales.annotatedapp.actions;

import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.framework.action.DeleteItemActionDefinition;

public class DeleteActionBuilder extends AbstractActionBuilder<DeleteActionBuilder> {

    protected String name;

    public DeleteActionBuilder setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ActionDefinition definition() {
        DeleteItemActionDefinition actionDefinition = new DeleteItemActionDefinition();
        actionDefinition.setName(name);
        actionDefinition.setAvailability(availability);
        actionDefinition.setIcon(icon == null ? "icon-delete" : icon);
        return actionDefinition;
    }
}
