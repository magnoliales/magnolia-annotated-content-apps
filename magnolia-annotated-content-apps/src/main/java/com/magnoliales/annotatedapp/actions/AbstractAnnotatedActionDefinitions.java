package com.magnoliales.annotatedapp.actions;

import com.magnoliales.annotatedapp.TypeTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract public class AbstractAnnotatedActionDefinitions implements AnnotatedActionDefinitions {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractAnnotatedActionDefinitions.class);

    private String appName;
    private TypeTree typeTree;

    @Override
    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public void setTypeTree(TypeTree typeTree) {
        this.typeTree = typeTree;
    }

    protected String getAppName() {
        return appName;
    }

    protected TypeTree getTypeTree() {
        return typeTree;
    }
}
