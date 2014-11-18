package com.magnoliales.annotatedapp.column;

import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.column.definition.StatusColumnDefinition;

public class StatusColumnBuilder extends AbstractColumnBuilder<StatusColumnBuilder> {

    @Override
    public ColumnDefinition definition() {
        StatusColumnDefinition columnDefinition = new StatusColumnDefinition();
        columnDefinition.setName(name);
        columnDefinition.setWidth(45);
        return columnDefinition;
    }
}
