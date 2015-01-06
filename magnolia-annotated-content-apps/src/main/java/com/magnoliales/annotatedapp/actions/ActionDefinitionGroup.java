package com.magnoliales.annotatedapp.actions;

import info.magnolia.ui.api.action.ActionDefinition;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class ActionDefinitionGroup {

    private String groupName;

    private Collection<ActionDefinition> actionDefinitions;

    public ActionDefinitionGroup(String groupName, ActionDefinition... actionDefinitions) {
        this.groupName = groupName;
        this.actionDefinitions = Collections.unmodifiableCollection(Arrays.asList(actionDefinitions));
    }

    public String getGroupName() {
        return groupName;
    }

    public Collection<ActionDefinition> getActionDefinitions() {
        return actionDefinitions;
    }
}
