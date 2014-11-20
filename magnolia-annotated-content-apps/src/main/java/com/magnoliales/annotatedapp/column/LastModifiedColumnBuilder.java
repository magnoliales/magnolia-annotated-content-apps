package com.magnoliales.annotatedapp.column;

import info.magnolia.ui.workbench.column.DateColumnFormatter;
import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.column.definition.PropertyColumnDefinition;

public class LastModifiedColumnBuilder extends AbstractColumnBuilder<LastModifiedColumnBuilder> {

    @Override
    public ColumnDefinition definition() {
        PropertyColumnDefinition columnDefinition = new PropertyColumnDefinition();
        columnDefinition.setName(name);
        columnDefinition.setPropertyName("mgnl:lastModified");
        columnDefinition.setFormatterClass(DateColumnFormatter.class);
        columnDefinition.setWidth(width == -1 ? 150 : width);
        columnDefinition.setSortable(sortable);
        return columnDefinition;
    }
}