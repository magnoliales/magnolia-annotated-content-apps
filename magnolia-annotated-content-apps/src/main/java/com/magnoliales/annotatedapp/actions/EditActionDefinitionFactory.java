package com.magnoliales.annotatedapp.actions;

import com.magnoliales.annotatedapp.TypeTree;
import com.magnoliales.annotatedapp.dialog.AnnotatedFormDialogDefinitionProvider;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.api.action.ActionDefinition;
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

public class EditActionDefinitionFactory implements AnnotatedActionDefinitionFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(EditActionDefinitionFactory.class);

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
        for (Class<?> nodeClass : typeTree.getClasses()) {
            String nodeTypeName = nodeClass.getAnnotation(Node.class).jcrType();
            groups.add(new ActionGroup(nodeTypeName, new ActionDefinition[]{
                    getAddActionDefinition(nodeClass, nodeTypeName),
                    getEditActionDefinition(nodeClass, nodeTypeName),
                    getDeleteActionDefinition(nodeClass, nodeTypeName)
            }));
        }
        return groups;
    }

    private ActionDefinition getAddActionDefinition(Class<?> nodeClass, String nodeTypeName) {

        OpenCreateDialogActionDefinition actionDefinition = new OpenCreateDialogActionDefinition();

        String actionName = "add" + WordUtils.capitalize(nodeTypeName);
        String id = appName + ":" + actionName;

        LOGGER.info("Registering dialog '" + id + "'. Node type '" + nodeTypeName + "'");

        Components.getComponent(DialogDefinitionRegistry.class).register(
                new AnnotatedFormDialogDefinitionProvider(id, nodeClass)
        );

        actionDefinition.setName(actionName);
        actionDefinition.setDialogName(id);
        actionDefinition.setNodeType(nodeTypeName);
        actionDefinition.setIcon("icon-add-node-content");

        ConfiguredAvailabilityDefinition availabilityDefinition = new ConfiguredAvailabilityDefinition();
        if (typeTree.getRootType().equals(nodeClass)) {
            availabilityDefinition.setRoot(true);
            availabilityDefinition.setNodes(false);
        } else {
            availabilityDefinition.setRoot(false);
            availabilityDefinition.addNodeType(nodeTypeName);
        }
        actionDefinition.setAvailability(availabilityDefinition);

        return actionDefinition;
    }

    private ActionDefinition getEditActionDefinition(Class<?> nodeClass, String nodeTypeName) {

        OpenEditDialogActionDefinition actionDefinition = new OpenEditDialogActionDefinition();

        String actionName = "edit" + WordUtils.capitalize(nodeTypeName);
        String id = appName + ":" + actionName;

        LOGGER.info("Registering dialog '" + id + "'. Node type '" + nodeTypeName + "'");

        Components.getComponent(DialogDefinitionRegistry.class).register(
                new AnnotatedFormDialogDefinitionProvider(id, nodeClass)
        );

        actionDefinition.setName(actionName);
        actionDefinition.setDialogName(id);
        actionDefinition.setIcon("icon-edit");

        ConfiguredAvailabilityDefinition availabilityDefinition = new ConfiguredAvailabilityDefinition();
        availabilityDefinition.setRoot(false);
        availabilityDefinition.setNodes(true);
        availabilityDefinition.addNodeType(nodeTypeName);
        actionDefinition.setAvailability(availabilityDefinition);

        return actionDefinition;
    }

    private ActionDefinition getDeleteActionDefinition(Class<?> nodeClass, String nodeTypeName) {

        DeleteItemActionDefinition actionDefinition = new DeleteItemActionDefinition();

        String actionName = "delete" + WordUtils.capitalize(nodeTypeName);
        String id = appName + ":" + actionName;

        LOGGER.info("Registering dialog '" + id + "'. Node type '" + nodeTypeName + "'");

        Components.getComponent(DialogDefinitionRegistry.class).register(
                new AnnotatedFormDialogDefinitionProvider(id, nodeClass)
        );

        actionDefinition.setName(actionName);
        actionDefinition.setIcon("icon-delete");

        ConfiguredAvailabilityDefinition availabilityDefinition = new ConfiguredAvailabilityDefinition();
        availabilityDefinition.setRoot(false);
        availabilityDefinition.setNodes(true);
        availabilityDefinition.addNodeType(nodeTypeName);
        actionDefinition.setAvailability(availabilityDefinition);

        return actionDefinition;
    }
}
