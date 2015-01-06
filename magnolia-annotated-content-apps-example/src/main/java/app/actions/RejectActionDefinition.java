package app.actions;

import info.magnolia.ui.api.action.ConfiguredActionDefinition;

public class RejectActionDefinition extends ConfiguredActionDefinition {

    public RejectActionDefinition() {
        setName("reject");
        setIcon("icon-unmark");
        setImplementationClass(RejectAction.class);
        setAvailability(new RejectActionAvailabilityDefinition());
        setSuccessMessage("rejected");
    }
}
