package com.magnoliales.annotatedapp.actions;

import com.magnoliales.annotatedapp.TypeTree;

import java.util.List;

public interface AnnotatedActionDefinitions {

    void setAppName(String appName);

    void setTypeTree(TypeTree typeTree);

    public List<ActionDefinitionGroup> getGroups();
}
