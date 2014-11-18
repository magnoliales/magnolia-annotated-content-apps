package com.magnoliales.annotatedapp.column;

import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.column.definition.PropertyColumnDefinition;

public class PropertyColumnBuilder extends AbstractColumnBuilder<PropertyColumnBuilder> {

    private String propertyName;

    public PropertyColumnBuilder setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return this;
    }

    @Override
    public ColumnDefinition definition() {
        PropertyColumnDefinition columnDefinition = new PropertyColumnDefinition();
        columnDefinition.setName(name);
        columnDefinition.setPropertyName(propertyName);
        return columnDefinition;
    }
}
