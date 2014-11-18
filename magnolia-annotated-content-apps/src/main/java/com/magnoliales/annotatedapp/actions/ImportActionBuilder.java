package com.magnoliales.annotatedapp.actions;

import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.framework.action.OpenCreateDialogActionDefinition;

public class ImportActionBuilder extends AbstractActionBuilder<ImportActionBuilder> {

    @Override
    public ActionDefinition definition() {
        OpenCreateDialogActionDefinition actionDefinition = new OpenCreateDialogActionDefinition();
        actionDefinition.setDialogName("ui-admincentral:import");
        actionDefinition.setIcon(icon == null ? "icon-import" : icon);
        actionDefinition.setName("import");
        return actionDefinition;
    }
}
