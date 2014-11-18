package com.magnoliales.annotatedapp.actions;

import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.framework.action.ActivationAction;
import info.magnolia.ui.framework.action.ActivationActionDefinition;

public class ActivateActionBuilder extends AbstractActionBuilder<ActivateActionBuilder> {

    @Override
    public ActionDefinition definition() {
        ActivationActionDefinition actionDefinition = new ActivationActionDefinition();
        actionDefinition.setCatalog("versioned");
        actionDefinition.setImplementationClass(ActivationAction.class);
        actionDefinition.setName("activate");
        actionDefinition.setCommand("activate");
        actionDefinition.setIcon(icon == null ? "icon-publish-incl-sub" : icon);
        actionDefinition.setRecursive(true);
        actionDefinition.setAvailability(availability);
        return actionDefinition;
    }
}
