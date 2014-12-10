package com.magnoliales.annotatedapp.column;

import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.column.definition.PropertyColumnDefinition;

public class PropertyColumnBuilder extends AbstractColumnBuilder<PropertyColumnBuilder> {

    @Override
    public ColumnDefinition definition() {
        PropertyColumnDefinition columnDefinition = new PropertyColumnDefinition();
        columnDefinition.setName(getName());
        columnDefinition.setPropertyName(getPropertyName());
        columnDefinition.setWidth(getWidth(0));
        columnDefinition.setSortable(isSortable());
        columnDefinition.setExpandRatio(getExpandRatio());
        columnDefinition.setEditable(isEditable());
        return columnDefinition;
    }
}
