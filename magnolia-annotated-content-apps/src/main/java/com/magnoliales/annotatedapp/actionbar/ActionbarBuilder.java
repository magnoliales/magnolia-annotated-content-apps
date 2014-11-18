package com.magnoliales.annotatedapp.actionbar;

import com.magnoliales.annotatedapp.availability.AvailabilityBuilder;
import info.magnolia.ui.actionbar.definition.*;

import java.util.ArrayList;
import java.util.List;

public class ActionbarBuilder {

    private String name;
    private List<Group> groups = new ArrayList<Group>();

    public ActionbarBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ActionbarBuilder addGroup(String name, String... actionNames) {
        groups.add(new Group(name, actionNames));
        return this;
    }

    public ActionbarBuilder addGroup(String name, List<String> actionNames) {
        groups.add(new Group(name, actionNames.toArray(new String[actionNames.size()])));
        return this;
    }

    public ActionbarDefinition definition() {
        ConfiguredActionbarDefinition actionbarDefinition = new ConfiguredActionbarDefinition();
        ConfiguredActionbarSectionDefinition sectionDefinition = new ConfiguredActionbarSectionDefinition();
        sectionDefinition.setName("mainSection");
        sectionDefinition.setAvailability(new AvailabilityBuilder().setRoot(true).setNodes(true).definition());
        List<ActionbarGroupDefinition> groupDefinitions = new ArrayList<ActionbarGroupDefinition>();
        for (Group group : groups) {
            ConfiguredActionbarGroupDefinition groupDefinition = new ConfiguredActionbarGroupDefinition();
            groupDefinition.setName(group.name);
            for (String actionName : group.actionNames) {
                ConfiguredActionbarItemDefinition itemDefinition = new ConfiguredActionbarItemDefinition();
                itemDefinition.setName(actionName);
                groupDefinition.getItems().add(itemDefinition);
            }
            groupDefinitions.add(groupDefinition);
        }
        sectionDefinition.setGroups(groupDefinitions);
        actionbarDefinition.getSections().add(sectionDefinition);
        return actionbarDefinition;
    }

    private static class Group {
        public String name;
        public String[] actionNames;
        private Group(String name, String[] actionNames) {
            this.name = name;
            this.actionNames = actionNames;
        }
    }
}
