package app.actions;

import com.magnoliales.annotatedapp.actions.ActionDefinitionGroup;
import com.magnoliales.annotatedapp.actions.AnnotatedActionDefinitions;

import java.util.ArrayList;
import java.util.List;

public class ApproveActionDefinitions extends AnnotatedActionDefinitions {

    @Override
    protected List<ActionDefinitionGroup> createActionDefinitionGroups() {
        List<ActionDefinitionGroup> groups = new ArrayList<>();
        groups.add(new ActionDefinitionGroup("approve",
                new ViewPageActionDefinition()
        ));
        return groups;
    }
}
