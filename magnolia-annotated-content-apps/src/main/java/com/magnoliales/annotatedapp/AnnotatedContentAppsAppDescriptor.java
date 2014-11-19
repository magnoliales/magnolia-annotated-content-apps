package com.magnoliales.annotatedapp;

import com.magnoliales.annotatedapp.actionbar.ActionbarBuilder;
import com.magnoliales.annotatedapp.actions.*;
import com.magnoliales.annotatedapp.availability.AvailabilityBuilder;
import com.magnoliales.annotatedapp.column.LastModifiedColumnBuilder;
import com.magnoliales.annotatedapp.column.PropertyColumnBuilder;
import com.magnoliales.annotatedapp.column.StatusColumnBuilder;
import com.magnoliales.annotatedapp.dialog.AnnotatedFormDialogDefinition;
import com.magnoliales.annotatedapp.node.AnnotatedNodeTypeDefinition;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.api.app.SubAppDescriptor;
import info.magnolia.ui.api.app.registry.ConfiguredAppDescriptor;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.contentapp.ContentApp;
import info.magnolia.ui.contentapp.browser.BrowserSubApp;
import info.magnolia.ui.contentapp.browser.ConfiguredBrowserSubAppDescriptor;
import info.magnolia.ui.dialog.definition.FormDialogDefinition;
import info.magnolia.ui.framework.app.BaseApp;
import info.magnolia.ui.vaadin.integration.contentconnector.ConfiguredJcrContentConnectorDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.ContentConnectorDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.NodeTypeDefinition;
import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.definition.ConfiguredWorkbenchDefinition;
import info.magnolia.ui.workbench.definition.ContentPresenterDefinition;
import info.magnolia.ui.workbench.definition.WorkbenchDefinition;
import info.magnolia.ui.workbench.tree.TreePresenterDefinition;
import info.magnolia.ui.workbench.tree.drop.DropConstraint;
import org.apache.commons.lang.WordUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotatedContentAppsAppDescriptor extends ConfiguredAppDescriptor {

    protected TypeTree typeTree;
    protected Class<? extends DropConstraint> dropConstraintClass;
    protected AnnotatedFormDialogDefinition[] dialogs;

    BaseApp a;

    public AnnotatedContentAppsAppDescriptor(Class<?> nodeClass, Class<? extends DropConstraint> dropConstraintClass,
                                             AnnotatedFormDialogDefinition[] dialogs) {

        this.dropConstraintClass = dropConstraintClass;
        this.dialogs = dialogs;

        typeTree = TypeTree.read(nodeClass);
        UI.App appAnnotation = nodeClass.getAnnotation(UI.App.class);
        String appName = appAnnotation.name();
        String theme = appAnnotation.theme();
        String workspace = appAnnotation.workspace();

        setName(appName);
        setAppClass(ContentApp.class);
        if(!theme.equalsIgnoreCase("")){
            setTheme(theme);
        }

        UI.Presenter.Column[] columnAnnotations = appAnnotation.columns();
        getSubApps().put("browser", getBrowser(appName, workspace, columnAnnotations));
    }

    private SubAppDescriptor getBrowser(String appName, String workspace, UI.Presenter.Column[] columnAnnotations) {
        ConfiguredBrowserSubAppDescriptor browser = new ConfiguredBrowserSubAppDescriptor();

        browser.setSubAppClass(BrowserSubApp.class);
        browser.setWorkbench(getWorkbench(appName, columnAnnotations));
        browser.setName("browser");
        // @todo content connectector
        browser.setContentConnector(getContentConnector(appName, workspace));

        ActionbarBuilder actionbarBuilder = new ActionbarBuilder().setName(getName());
        Map<String, ActionDefinition> actions = new HashMap<String, ActionDefinition>();

        for (TypeTree typeSubTree : typeTree.toList()) {

            String nodeTypeName = typeSubTree.getNodeTypeName();
            String capitalizedNodeTypeName = WordUtils.capitalize(nodeTypeName);
            String addActionName = "add" + capitalizedNodeTypeName;
            String editActionName = "edit" + capitalizedNodeTypeName;
            String deleteActionName = "delete" + capitalizedNodeTypeName;

            AvailabilityDefinition addActionAvailability;
            if (!typeSubTree.hasParent()) {
                addActionAvailability = new AvailabilityBuilder()
                        .setRoot(true)
                        .setNodes(false)
                        .definition();
            } else {
                addActionAvailability = new AvailabilityBuilder()
                        .setRoot(false)
                        .addNodeType(typeSubTree.getParent().getNodeTypeName())
                        .definition();
            }
            AvailabilityDefinition editAndDeleteActionAvailability = new AvailabilityBuilder()
                    .setRoot(false)
                    .setNodes(true)
                    .addNodeType(nodeTypeName)
                    .definition();

            FormDialogDefinition formDialogDefinition = getFormDialogDefinition(typeSubTree.getRootType());
            ActionDefinition addAction = new AddActionBuilder()
                    .setAppName(getName())
                    .setName(addActionName)
                    .setFormDialogDefinition(formDialogDefinition)
                    .setNodeType(nodeTypeName)
                    .setAvailability(addActionAvailability)
                    .definition();
            ActionDefinition editAction = new EditActionBuilder()
                    .setAppName(getName())
                    .setName(editActionName)
                    .setFormDialogDefinition(formDialogDefinition)
                    .setAvailability(editAndDeleteActionAvailability)
                    .definition();
            ActionDefinition deleteAction = new DeleteActionBuilder()
                    .setName(deleteActionName)
                    .setAvailability(editAndDeleteActionAvailability)
                    .definition();

            actions.put(addActionName, addAction);
            actions.put(editActionName, editAction);
            actions.put(deleteActionName, deleteAction);

            actionbarBuilder.addGroup(nodeTypeName, addActionName, editActionName, deleteActionName);
        }

        AvailabilityDefinition completeAvailability = new AvailabilityBuilder().setRoot(true).setNodes(true).definition();

        actions.put("export", new ExportActionBuilder().setAvailability(completeAvailability).definition());
        actions.put("import", new ImportActionBuilder().setAvailability(completeAvailability).definition());
        actionbarBuilder.addGroup("export", "export", "import");

        if (!typeTree.getRootType().isAnnotationPresent(UI.NoActivation.class)) {
            AvailabilityDefinition nodesAvailability = new AvailabilityBuilder().setRoot(false).setNodes(true).definition();
            actions.put("activate", new ActivateActionBuilder().setAvailability(nodesAvailability).definition());
            actionbarBuilder.addGroup("activate", "activate");
        }

        browser.setActions(actions);
        browser.setActionbar(actionbarBuilder.definition());

        return browser;
    }

    private AnnotatedFormDialogDefinition getFormDialogDefinition(Class<?> nodeClass) {
        for (AnnotatedFormDialogDefinition dialog : dialogs) {
            if (dialog.getNodeClass().equals(nodeClass)) {
                return dialog;
            }
        }
        return null; // @todo throw exception;
    }

    private List<NodeTypeDefinition> getNodeTypeDefinitions(String appName, TypeTree typeTree) {
        // why do we need appName here?
        List<NodeTypeDefinition> nodeTypeDefinitions = new ArrayList<NodeTypeDefinition>();
        for (String nodeTypeName : typeTree.getNodeTypeNames()) {
            AnnotatedNodeTypeDefinition nodeTypeDefinition = new AnnotatedNodeTypeDefinition();
            nodeTypeDefinition.setName(nodeTypeName);
            nodeTypeDefinitions.add(nodeTypeDefinition);
        }
        return nodeTypeDefinitions;
    }

    private WorkbenchDefinition getWorkbench(String appName, UI.Presenter.Column[] columnAnnotations) {
        ConfiguredWorkbenchDefinition workbench = new ConfiguredWorkbenchDefinition();
        workbench.setName("workbench");
        workbench.setDropConstraintClass(dropConstraintClass);
        List<ContentPresenterDefinition> presenters = new ArrayList<ContentPresenterDefinition>();
        presenters.add(getTree(columnAnnotations));
        workbench.setContentViews(presenters);

        return workbench;
    }

    private ContentConnectorDefinition getContentConnector(String appName, String workspace) {
        ConfiguredJcrContentConnectorDefinition contentConnector = new ConfiguredJcrContentConnectorDefinition();
        contentConnector.setRootPath("/");
        contentConnector.setWorkspace(workspace);
        contentConnector.setNodeTypes(getNodeTypeDefinitions(appName, typeTree));
        contentConnector.setIncludeProperties(false);
        return contentConnector;
    }

    private ContentPresenterDefinition getTree(UI.Presenter.Column[] columnAnnotations) {
        TreePresenterDefinition tree = new TreePresenterDefinition();
        List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
        Map<String, String> fieldMapping = typeTree.getFieldMapping();
        for (UI.Presenter.Column columnAnnotation : columnAnnotations) {
            String name = columnAnnotation.name();
            if (columnAnnotation.builder().equals(PropertyColumnBuilder.class)) {
                ColumnDefinition column = new PropertyColumnBuilder()
                        .setName(name)
                        .setPropertyName(fieldMapping.get(name))
                        .definition();
                columns.add(column);
            } else if (columnAnnotation.builder().equals(StatusColumnBuilder.class)) {
                ColumnDefinition column = new StatusColumnBuilder()
                        .setName(name)
                        .definition();
                columns.add(column);
            } else if (columnAnnotation.builder().equals(LastModifiedColumnBuilder.class)) {
                ColumnDefinition column = new LastModifiedColumnBuilder()
                        .setName(name)
                        .definition();
                columns.add(column);
            }

            // @todo scream an we don't know what to do with the column
        }
        tree.setColumns(columns);
        return tree;
    }
}
