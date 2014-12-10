package com.magnoliales.annotatedapp.column;

import info.magnolia.ui.workbench.column.definition.ColumnDefinition;

public abstract class AbstractColumnBuilder<T> {

    private String name;
    private String propertyName;
    private int width = -1;
    private float expandRatio;
    private boolean sortable;
    private boolean editable;

    @SuppressWarnings("unchecked")
    public T setName(String name) {
        this.name = name;
        return (T) this;
    }

    protected String getName() {
        return name;
    }

    @SuppressWarnings("unchecked")
    public T setPropertyName(String propertyName) {
        this.propertyName = propertyName;
        return (T) this;
    }

    protected String getPropertyName() {
        return propertyName;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    protected int getWidth(int defaultWidth) {
        return width == -1 ? defaultWidth : width;
    }

    public void setExpandRatio(float expandRatio) {
        this.expandRatio = expandRatio;
    }

    protected float getExpandRatio() {
        return expandRatio;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    protected boolean isSortable() {
        return sortable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    protected boolean isEditable() {
        return editable;
    }

    public abstract ColumnDefinition definition();
}
