package com.magnoliales.annotatedapp.column;

import info.magnolia.ui.workbench.column.definition.ColumnDefinition;

abstract public class AbstractColumnBuilder<T> {

    protected String name;
    protected String propertyName;
    protected int width;
    protected float expandRatio;
    protected boolean sortable;
    protected boolean editable;

    @SuppressWarnings("unchecked")
    public T setName(String name) {
        this.name = name;
        return (T)this;
    }

    public T setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return (T)this;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setExpandRatio(float expandRatio) {
        this.expandRatio = expandRatio;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    abstract public ColumnDefinition definition();
}
