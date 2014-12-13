package com.magnoliales.annotatedapp.actions;

import info.magnolia.ui.api.action.ActionDefinition;

public class ActionDefinitionGroup {

    private String groupName;

    private ActionDefinition[] actionDefinitions;

    public ActionDefinitionGroup(String groupName, ActionDefinition[] actionDefinitions) {
        this.groupName = groupName;
        this.actionDefinitions = actionDefinitions;
    }

    public String getGroupName() {
        return groupName;
    }

    public ActionDefinition[] getActionDefinitions() {
        return actionDefinitions;
    }
}
