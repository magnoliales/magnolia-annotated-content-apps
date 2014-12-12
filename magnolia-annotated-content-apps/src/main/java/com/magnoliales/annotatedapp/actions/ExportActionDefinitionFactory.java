package com.magnoliales.annotatedapp.actions;

import com.magnoliales.annotatedapp.TypeTree;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.api.availability.ConfiguredAvailabilityDefinition;
import info.magnolia.ui.framework.action.ExportActionDefinition;
import info.magnolia.ui.framework.action.OpenCreateDialogActionDefinition;
import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ExportActionDefinitionFactory implements AnnotatedActionDefinitionFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportActionDefinitionFactory.class);

    private String appName;
    private TypeTree typeTree;

    @Override
    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public void setTypeTree(TypeTree typeTree) {
        this.typeTree = typeTree;
    }

    @Override
    public List<ActionGroup> getGroups() {
        List<ActionGroup> groups = new ArrayList<>();
        groups.add(new ActionGroup("export", new ActionDefinition[] {
            getExportActionDefinition(),
            getImportActionDefinition()
        }));
        return groups;
    }

    private ActionDefinition getExportActionDefinition() {

        ExportActionDefinition actionDefinition = new ExportActionDefinition();

        String actionName = "export" + WordUtils.capitalize(appName);

        actionDefinition.setName(actionName);
        actionDefinition.setCommand("export");
        actionDefinition.setIcon("icon-export");

        ConfiguredAvailabilityDefinition availabilityDefinition = new ConfiguredAvailabilityDefinition();
        availabilityDefinition.setRoot(true);
        availabilityDefinition.setNodes(true);
        actionDefinition.setAvailability(availabilityDefinition);

        return actionDefinition;
    }

    private ActionDefinition getImportActionDefinition() {

        OpenCreateDialogActionDefinition actionDefinition = new OpenCreateDialogActionDefinition();

        String actionName = "import" + WordUtils.capitalize(appName);

        actionDefinition.setName(actionName);
        actionDefinition.setDialogName("ui-admincentral:import");
        actionDefinition.setIcon("icon-import");

        ConfiguredAvailabilityDefinition availabilityDefinition = new ConfiguredAvailabilityDefinition();
        availabilityDefinition.setRoot(true);
        availabilityDefinition.setNodes(true);
        actionDefinition.setAvailability(availabilityDefinition);

        return actionDefinition;
    }
}
