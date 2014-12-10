package com.magnoliales.annotatedapp.column;

import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.ui.workbench.column.DateColumnFormatter;
import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.column.definition.PropertyColumnDefinition;

public class LastModifiedColumnBuilder extends AbstractColumnBuilder<LastModifiedColumnBuilder> {

    private static final int DEFAULT_WIDTH = 150;

    @Override
    public ColumnDefinition definition() {
        PropertyColumnDefinition columnDefinition = new PropertyColumnDefinition();
        columnDefinition.setName(getName());
        columnDefinition.setPropertyName(NodeTypes.LastModified.NAME);
        columnDefinition.setFormatterClass(DateColumnFormatter.class);
        columnDefinition.setWidth(getWidth(DEFAULT_WIDTH));
        columnDefinition.setSortable(isSortable());
        return columnDefinition;
    }
}