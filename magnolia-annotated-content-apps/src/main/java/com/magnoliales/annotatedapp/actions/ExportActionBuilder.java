package com.magnoliales.annotatedapp.actions;

import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.framework.action.ExportActionDefinition;

public class ExportActionBuilder extends AbstractActionBuilder<ExportActionBuilder> {
    @Override
    public ActionDefinition definition() {
        ExportActionDefinition actionDefinition = new ExportActionDefinition();
        actionDefinition.setCommand("export");
        actionDefinition.setName("export");
        actionDefinition.setAvailability(availability);
        actionDefinition.setIcon(icon == null ? "icon-export" : icon);
        return actionDefinition;
    }
}
