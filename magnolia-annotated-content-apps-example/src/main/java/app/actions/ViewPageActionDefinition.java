package app.actions;

import info.magnolia.ui.api.action.ConfiguredActionDefinition;

public class ViewPageActionDefinition extends ConfiguredActionDefinition {

    public ViewPageActionDefinition() {
        setName("viewPage");
        setIcon("icon-link-page");
        setImplementationClass(ViewPageAction.class);
    }
}
