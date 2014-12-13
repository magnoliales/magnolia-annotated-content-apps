package com.magnoliales.annotatedapp.actions;

import com.magnoliales.annotatedapp.TypeTree;

import java.util.List;

abstract public class AnnotatedActionDefinitions {

    private String appName;
    private TypeTree typeTree;

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

    public abstract List<ActionDefinitionGroup> getActionDefinitionGroups();
}
