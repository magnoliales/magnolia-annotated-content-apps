package app.actions;

import com.magnoliales.annotatedapp.actions.ActionDefinitionGroup;
import com.magnoliales.annotatedapp.actions.AnnotatedActionDefinitions;

import java.util.ArrayList;
import java.util.List;

public class ApprovalActionDefinitions extends AnnotatedActionDefinitions {

    @Override
    protected List<ActionDefinitionGroup> createActionDefinitionGroups() {
        List<ActionDefinitionGroup> groups = new ArrayList<>();
        groups.add(new ActionDefinitionGroup("approval",
                new ApproveActionDefinition(),
                new RejectActionDefinition(),
                new ViewPageActionDefinition()
        ));
        return groups;
    }
}
