package com.magnoliales.annotatedapp.node;

import info.magnolia.i18nsystem.I18nText;
import info.magnolia.ui.vaadin.integration.contentconnector.ConfiguredNodeTypeDefinition;

public class AnnotatedNodeTypeDefinition extends ConfiguredNodeTypeDefinition {

    @I18nText
    public String getIcon() {
        return super.getIcon();
    }
}
