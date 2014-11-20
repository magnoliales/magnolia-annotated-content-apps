package com.magnoliales.annotatedapp.column;

import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.column.definition.PropertyColumnDefinition;

public class PropertyColumnBuilder extends AbstractColumnBuilder<PropertyColumnBuilder> {

    @Override
    public ColumnDefinition definition() {
        PropertyColumnDefinition columnDefinition = new PropertyColumnDefinition();
        columnDefinition.setName(name);
        columnDefinition.setPropertyName(propertyName);
        columnDefinition.setWidth(width);
        columnDefinition.setSortable(sortable);
        columnDefinition.setExpandRatio(expandRatio);
        columnDefinition.setEditable(editable);
        return columnDefinition;
    }
}
