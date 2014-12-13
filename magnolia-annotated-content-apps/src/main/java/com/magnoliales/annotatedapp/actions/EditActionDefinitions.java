package com.magnoliales.annotatedapp.actions;

import com.magnoliales.annotatedapp.dialog.AnnotatedFormDialogDefinitionProvider;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.api.availability.ConfiguredAvailabilityDefinition;
import info.magnolia.ui.dialog.registry.DialogDefinitionRegistry;
import info.magnolia.ui.framework.action.DeleteItemActionDefinition;
import info.magnolia.ui.framework.action.OpenCreateDialogActionDefinition;
import info.magnolia.ui.framework.action.OpenEditDialogActionDefinition;
import org.apache.commons.lang.WordUtils;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EditActionDefinitions extends AnnotatedActionDefinitions {

    private static final Logger LOGGER = LoggerFactory.getLogger(EditActionDefinitions.class);

    @Override
    public List<ActionDefinitionGroup> getActionDefinitionGroups() {
        List<ActionDefinitionGroup> groups = new ArrayList<>();
        for (Class<?> nodeClass : getTypeTree().getClasses()) {
            String nodeTypeName = nodeClass.getAnnotation(Node.class).jcrType();
            groups.add(new ActionDefinitionGroup(nodeTypeName, new ActionDefinition[]{
                    getAddActionDefinition(nodeClass, nodeTypeName),
                    getEditActionDefinition(nodeClass, nodeTypeName),
                    getDeleteActionDefinition(nodeClass, nodeTypeName)
            }));
        }
        return groups;
    }

    protected ActionDefinition getAddActionDefinition(Class<?> nodeClass, String nodeTypeName) {

        OpenCreateDialogActionDefinition actionDefinition = new OpenCreateDialogActionDefinition();

        String actionName = "add" + WordUtils.capitalize(nodeTypeName);
        String id = getAppName() + ":" + actionName;

        LOGGER.info("Registering dialog '" + id + "'. Node type '" + nodeTypeName + "'");

        Components.getComponent(DialogDefinitionRegistry.class).register(
                new AnnotatedFormDialogDefinitionProvider(id, nodeClass)
        );

        actionDefinition.setName(actionName);
        actionDefinition.setDialogName(id);
        actionDefinition.setNodeType(nodeTypeName);
        actionDefinition.setIcon("icon-add-node-content");

        boolean isRootType = getTypeTree().getRootType().equals(nodeClass);
        actionDefinition.setAvailability(getAddActionAvailabilityDefinition(isRootType, nodeTypeName));

        return actionDefinition;
    }

    protected AvailabilityDefinition getAddActionAvailabilityDefinition(boolean isRootType, String nodeTypeName) {
        ConfiguredAvailabilityDefinition availabilityDefinition = new ConfiguredAvailabilityDefinition();
        if (isRootType) {
            availabilityDefinition.setRoot(true);
            availabilityDefinition.setNodes(false);
        } else {
            availabilityDefinition.setRoot(false);
            availabilityDefinition.addNodeType(nodeTypeName);
        }
        return availabilityDefinition;
    }

    protected ActionDefinition getEditActionDefinition(Class<?> nodeClass, String nodeTypeName) {

        OpenEditDialogActionDefinition actionDefinition = new OpenEditDialogActionDefinition();

        String actionName = "edit" + WordUtils.capitalize(nodeTypeName);
        String id = getAppName() + ":" + actionName;

        LOGGER.info("Registering dialog '" + id + "'. Node type '" + nodeTypeName + "'");

        Components.getComponent(DialogDefinitionRegistry.class).register(
                new AnnotatedFormDialogDefinitionProvider(id, nodeClass)
        );

        actionDefinition.setName(actionName);
        actionDefinition.setDialogName(id);
        actionDefinition.setIcon("icon-edit");
        actionDefinition.setAvailability(getEditActionAvailabilityDefinition(nodeTypeName));

        return actionDefinition;
    }

    protected AvailabilityDefinition getEditActionAvailabilityDefinition(String nodeTypeName) {
        ConfiguredAvailabilityDefinition availabilityDefinition = new ConfiguredAvailabilityDefinition();
        availabilityDefinition.setRoot(false);
        availabilityDefinition.setNodes(true);
        availabilityDefinition.addNodeType(nodeTypeName);
        return availabilityDefinition;
    }

    protected ActionDefinition getDeleteActionDefinition(Class<?> nodeClass, String nodeTypeName) {

        DeleteItemActionDefinition actionDefinition = new DeleteItemActionDefinition();

        String actionName = "delete" + WordUtils.capitalize(nodeTypeName);
        String id = getAppName() + ":" + actionName;

        LOGGER.info("Registering dialog '" + id + "'. Node type '" + nodeTypeName + "'");

        Components.getComponent(DialogDefinitionRegistry.class).register(
                new AnnotatedFormDialogDefinitionProvider(id, nodeClass)
        );

        actionDefinition.setName(actionName);
        actionDefinition.setIcon("icon-delete");
        actionDefinition.setAvailability(getDeleteActionAvailabilityDefinition(nodeTypeName));

        return actionDefinition;
    }

    protected AvailabilityDefinition getDeleteActionAvailabilityDefinition(String nodeTypeName) {
        ConfiguredAvailabilityDefinition availabilityDefinition = new ConfiguredAvailabilityDefinition();
        availabilityDefinition.setRoot(false);
        availabilityDefinition.setNodes(true);
        availabilityDefinition.addNodeType(nodeTypeName);
        return availabilityDefinition;
    }
}
