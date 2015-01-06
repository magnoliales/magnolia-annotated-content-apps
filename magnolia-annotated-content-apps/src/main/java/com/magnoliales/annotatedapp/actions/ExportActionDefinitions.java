package com.magnoliales.annotatedapp.actions;

import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.api.availability.ConfiguredAvailabilityDefinition;
import info.magnolia.ui.framework.action.ExportActionDefinition;
import info.magnolia.ui.framework.action.OpenCreateDialogActionDefinition;
import org.apache.commons.lang.WordUtils;

import java.util.ArrayList;
import java.util.List;

public class ExportActionDefinitions extends AnnotatedActionDefinitions {

    @Override
    protected List<ActionDefinitionGroup> createActionDefinitionGroups() {
        List<ActionDefinitionGroup> groups = new ArrayList<>();
        groups.add(new ActionDefinitionGroup("export",
                getExportActionDefinition(),
                getImportActionDefinition()
        ));
        return groups;
    }

    protected ActionDefinition getExportActionDefinition() {

        ExportActionDefinition actionDefinition = new ExportActionDefinition();

        String actionName = "export" + WordUtils.capitalize(getAppName());

        actionDefinition.setName(actionName);
        actionDefinition.setCommand("export");
        actionDefinition.setIcon("icon-export");
        actionDefinition.setAvailability(getExportActionAvailabilityDefinition());

        return actionDefinition;
    }

    protected AvailabilityDefinition getExportActionAvailabilityDefinition() {
        ConfiguredAvailabilityDefinition availabilityDefinition = new ConfiguredAvailabilityDefinition();
        availabilityDefinition.setRoot(true);
        availabilityDefinition.setNodes(true);
        return availabilityDefinition;
    }

    protected ActionDefinition getImportActionDefinition() {

        OpenCreateDialogActionDefinition actionDefinition = new OpenCreateDialogActionDefinition();

        String actionName = "import" + WordUtils.capitalize(getAppName());

        actionDefinition.setName(actionName);
        actionDefinition.setDialogName("ui-admincentral:import");
        actionDefinition.setIcon("icon-import");
        actionDefinition.setAvailability(getImportActionAvailabilityDefinition());

        return actionDefinition;
    }

    protected AvailabilityDefinition getImportActionAvailabilityDefinition() {
        return getExportActionAvailabilityDefinition();
    }
}
