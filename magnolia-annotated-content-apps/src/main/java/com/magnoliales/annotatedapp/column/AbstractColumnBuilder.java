package com.magnoliales.annotatedapp.column;

import info.magnolia.ui.workbench.column.definition.ColumnDefinition;

abstract public class AbstractColumnBuilder<T> {

    protected String name;

    @SuppressWarnings("unchecked")
    public T setName(String name) {
        this.name = name;
        return (T)this;
    }

    abstract public ColumnDefinition definition();
}
