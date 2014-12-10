package com.magnoliales.annotatedapp.column;

import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.column.definition.StatusColumnDefinition;

public class StatusColumnBuilder extends AbstractColumnBuilder<StatusColumnBuilder> {

    private static final int DEFAULT_WIDTH = 45;

    @Override
    public ColumnDefinition definition() {
        StatusColumnDefinition columnDefinition = new StatusColumnDefinition();
        columnDefinition.setName(getName());
        columnDefinition.setWidth(getWidth(DEFAULT_WIDTH));
        columnDefinition.setSortable(isSortable());
        return columnDefinition;
    }
}
