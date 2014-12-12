package com.magnoliales.annotatedapp.actions;

import com.magnoliales.annotatedapp.TypeTree;
import info.magnolia.ui.api.action.ActionDefinition;

import java.util.ArrayList;
import java.util.List;

public interface AnnotatedActionDefinitionFactory {

    void setAppName(String appName);

    void setTypeTree(TypeTree typeTree);

    /**
     * Get action groups within specified section.
     *
     * @todo simplify to make sure that the groups can be easily combined, and we get rid of ugly string constants
     * @return list of groups withing specified section
     * @throws java.lang.IllegalArgumentException if the section is unknown
     */
    public List<ActionGroup> getGroups();

    public class ActionGroup {

        private String groupName;

        private ActionDefinition[] actionDefinitions;

        public ActionGroup(String groupName, ActionDefinition[] actionDefinitions) {
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
}
