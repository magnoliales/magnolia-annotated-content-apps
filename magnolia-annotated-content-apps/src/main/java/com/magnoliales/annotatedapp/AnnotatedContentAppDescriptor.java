package com.magnoliales.annotatedapp;

import com.magnoliales.annotatedapp.actions.ActionDefinitionGroup;
import com.magnoliales.annotatedapp.actions.AnnotatedActionDefinitions;
import com.magnoliales.annotatedapp.column.AbstractColumnBuilder;
import com.magnoliales.annotatedapp.node.AnnotatedNodeTypeDefinition;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarGroupDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarItemDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarSectionDefinition;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.api.app.SubAppDescriptor;
import info.magnolia.ui.api.app.registry.ConfiguredAppDescriptor;
import info.magnolia.ui.api.availability.ConfiguredAvailabilityDefinition;
import info.magnolia.ui.contentapp.ContentApp;
import info.magnolia.ui.contentapp.browser.BrowserSubApp;
import info.magnolia.ui.contentapp.browser.ConfiguredBrowserSubAppDescriptor;
import info.magnolia.ui.dialog.definition.ConfiguredFormDialogDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.ConfiguredJcrContentConnectorDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.ContentConnectorDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.NodeTypeDefinition;
import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.definition.ConfiguredWorkbenchDefinition;
import info.magnolia.ui.workbench.definition.ContentPresenterDefinition;
import info.magnolia.ui.workbench.definition.WorkbenchDefinition;
import info.magnolia.ui.workbench.list.ListPresenterDefinition;
import info.magnolia.ui.workbench.search.SearchPresenterDefinition;
import info.magnolia.ui.workbench.tree.TreePresenterDefinition;
import info.magnolia.ui.workbench.tree.drop.DropConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotatedContentAppDescriptor extends ConfiguredAppDescriptor {

    private static final long serialVersionUID = 7496221475L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguredFormDialogDefinition.class);

    private TypeTree typeTree;
    private Class<? extends DropConstraint> dropConstraintClass;

    public AnnotatedContentAppDescriptor(Class<?> nodeClass, Class<? extends DropConstraint> dropConstraintClass) {

        this.dropConstraintClass = dropConstraintClass;

        typeTree = TypeTree.read(nodeClass);
        UI.App appAnnotation = nodeClass.getAnnotation(UI.App.class);
        String appName = appAnnotation.name();
        String theme = appAnnotation.theme();
        String workspace = appAnnotation.workspace();

        setName(appName);
        setAppClass(ContentApp.class);
        if (theme != null && !theme.isEmpty()) {
            setTheme(theme);
        }

        UI.Presenter.Column[] columnAnnotations = appAnnotation.columns();
        Class<? extends AnnotatedActionDefinitions>[] actionFactories = appAnnotation.actions();
        getSubApps().put("browser", getBrowser(workspace, columnAnnotations, actionFactories));
    }

    private SubAppDescriptor getBrowser(String workspace,
                                        UI.Presenter.Column[] columnAnnotations,
                                        Class<? extends AnnotatedActionDefinitions>[] actionFactories) {

        ConfiguredBrowserSubAppDescriptor browser = new ConfiguredBrowserSubAppDescriptor();

        browser.setSubAppClass(BrowserSubApp.class);
        browser.setWorkbench(getWorkbench(columnAnnotations));
        browser.setName("browser");

        // @todo content connector
        browser.setContentConnector(getContentConnector(workspace));

        Map<String, ActionDefinition> actions = new HashMap<>();

        ConfiguredActionbarDefinition actionbarDefinition = new ConfiguredActionbarDefinition();
        ConfiguredAvailabilityDefinition availabilityDefinition = new ConfiguredAvailabilityDefinition();
        availabilityDefinition.setRoot(true);
        availabilityDefinition.setNodes(true);
        ConfiguredActionbarSectionDefinition sectionDefinition = new ConfiguredActionbarSectionDefinition();
        sectionDefinition.setName("main");
        sectionDefinition.setAvailability(availabilityDefinition);
        for (Class<? extends AnnotatedActionDefinitions> actionDefinitionFactoryClass : actionFactories) {
            AnnotatedActionDefinitions actionDefinitionFactory;
            try {
                actionDefinitionFactory = actionDefinitionFactoryClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalStateException("Cannot instantiate annotations factory", e);
            }
            actionDefinitionFactory.setAppName(getName());
            actionDefinitionFactory.setTypeTree(typeTree);
            for (ActionDefinitionGroup actionDefinitionGroup : actionDefinitionFactory.getGroups()) {
                ConfiguredActionbarGroupDefinition groupDefinition = new ConfiguredActionbarGroupDefinition();
                groupDefinition.setName(actionDefinitionGroup.getGroupName());
                for (ActionDefinition actionDefinition : actionDefinitionGroup.getActionDefinitions()) {
                    actions.put(actionDefinition.getName(), actionDefinition);
                    ConfiguredActionbarItemDefinition itemDefinition = new ConfiguredActionbarItemDefinition();
                    itemDefinition.setName(actionDefinition.getName());
                    groupDefinition.addItem(itemDefinition);
                }
                sectionDefinition.addGroup(groupDefinition);
            }
        }
        actionbarDefinition.addSection(sectionDefinition);

        browser.setActions(actions);
        browser.setActionbar(actionbarDefinition);

        return browser;
    }

    private List<NodeTypeDefinition> getNodeTypeDefinitions(TypeTree typeTree) {
        List<NodeTypeDefinition> nodeTypeDefinitions = new ArrayList<>();
        for (String nodeTypeName : typeTree.getNodeTypeNames()) {
            AnnotatedNodeTypeDefinition nodeTypeDefinition = new AnnotatedNodeTypeDefinition();
            nodeTypeDefinition.setName(nodeTypeName);
            nodeTypeDefinitions.add(nodeTypeDefinition);
        }
        return nodeTypeDefinitions;
    }

    private WorkbenchDefinition getWorkbench(UI.Presenter.Column[] columnAnnotations) {
        ConfiguredWorkbenchDefinition workbench = new ConfiguredWorkbenchDefinition();
        workbench.setName("workbench");
        workbench.setDropConstraintClass(dropConstraintClass);
        List<ContentPresenterDefinition> presenters = new ArrayList<>();
        presenters.add(getTreePresenter(columnAnnotations));
        presenters.add(getListPresenter(columnAnnotations));
        presenters.add(getSearchPresenter(columnAnnotations));
        workbench.setContentViews(presenters);
        return workbench;
    }

    private ContentConnectorDefinition getContentConnector(String workspace) {
        ConfiguredJcrContentConnectorDefinition contentConnector = new ConfiguredJcrContentConnectorDefinition();
        contentConnector.setRootPath("/");
        contentConnector.setWorkspace(workspace);
        contentConnector.setNodeTypes(getNodeTypeDefinitions(typeTree));
        contentConnector.setDefaultOrder("jcrName");
        contentConnector.setIncludeProperties(false);
        return contentConnector;
    }

    private ContentPresenterDefinition getTreePresenter(UI.Presenter.Column[] columnAnnotations) {
        TreePresenterDefinition tree = new TreePresenterDefinition();
        tree.setColumns(getPresenterColumns(columnAnnotations));
        return tree;
    }

    private ContentPresenterDefinition getListPresenter(UI.Presenter.Column[] columnAnnotations) {
        ListPresenterDefinition list = new ListPresenterDefinition();
        list.setColumns(getPresenterColumns(columnAnnotations));
        return list;
    }

    private ContentPresenterDefinition getSearchPresenter(UI.Presenter.Column[] columnAnnotations) {
        SearchPresenterDefinition searchPresenter = new SearchPresenterDefinition();
        searchPresenter.setColumns(getPresenterColumns(columnAnnotations));
        return searchPresenter;
    }

    private List<ColumnDefinition> getPresenterColumns(UI.Presenter.Column[] columnAnnotations) {
        List<ColumnDefinition> columns = new ArrayList<>();
        for (UI.Presenter.Column columnAnnotation : columnAnnotations) {
            Class<? extends AbstractColumnBuilder> builderClass = columnAnnotation.builder();
            AbstractColumnBuilder builder = null;
            try {
                builder = builderClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                LOGGER.error("Could not create a new instance of '"
                        + builderClass.getCanonicalName()
                        + "'", e);
            }
            if (builder != null) {
                builder.setName(columnAnnotation.name());
                builder.setPropertyName(columnAnnotation.property());
                builder.setWidth(columnAnnotation.width());
                builder.setEditable(columnAnnotation.editable());
                builder.setExpandRatio(columnAnnotation.expandRatio());
                builder.setSortable(columnAnnotation.sortable());
                columns.add(builder.definition());
            }
        }
        return  columns;
    }

    @Override
    @SuppressFBWarnings(justification = "Cannot change implementation in the parent class. "
            + "Pretty sure it is a non issue")
    public boolean equals(Object o) {
        if (o instanceof AnnotatedContentAppDescriptor) {
            AnnotatedContentAppDescriptor other = (AnnotatedContentAppDescriptor) o;
            return typeTree.getRootType().equals(other.typeTree.getRootType());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return typeTree.getRootType().hashCode();
    }
}
