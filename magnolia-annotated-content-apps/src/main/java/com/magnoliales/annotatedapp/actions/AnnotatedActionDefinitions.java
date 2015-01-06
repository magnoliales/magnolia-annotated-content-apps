package com.magnoliales.annotatedapp.actions;

import com.magnoliales.annotatedapp.TypeTree;

import java.util.List;

public abstract class AnnotatedActionDefinitions {

    private String appName;
    private TypeTree typeTree;
    private List<ActionDefinitionGroup> actionDefinitionGroups;

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setTypeTree(TypeTree typeTree) {
        this.typeTree = typeTree;
    }

    protected String getAppName() {
        return appName;
    }

    protected TypeTree getTypeTree() {
        return typeTree;
    }

    public List<ActionDefinitionGroup> getActionDefinitionGroups() {
        if (appName == null || typeTree == null) {
            throw new IllegalStateException("Action definition factory is not properly initialized");
        }
        if (actionDefinitionGroups == null) {
            actionDefinitionGroups = createActionDefinitionGroups();
        }
        return actionDefinitionGroups;
    }

    protected abstract List<ActionDefinitionGroup> createActionDefinitionGroups();
}
