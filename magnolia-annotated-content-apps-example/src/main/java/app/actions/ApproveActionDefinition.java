package app.actions;

import info.magnolia.ui.api.action.ConfiguredActionDefinition;

public class ApproveActionDefinition extends ConfiguredActionDefinition {

    public ApproveActionDefinition() {
        setName("approve");
        setIcon("icon-mark");
        setImplementationClass(ApproveAction.class);
        setAvailability(new ApproveActionAvailabilityDefinition());
        setSuccessMessage("approved");
    }
}
