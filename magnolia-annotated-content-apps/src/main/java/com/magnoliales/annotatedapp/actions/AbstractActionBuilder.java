package com.magnoliales.annotatedapp.actions;

import com.magnoliales.annotatedapp.availability.AvailabilityBuilder;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.api.availability.AvailabilityDefinition;

abstract public class AbstractActionBuilder<T> {

    protected AvailabilityDefinition availability = new AvailabilityBuilder().definition();
    protected String icon;

    @SuppressWarnings("unchecked")
    public T setIcon(String icon) {
        this.icon = icon;
        return (T)this;
    }

    @SuppressWarnings("unchecked")
    public T setAvailability(AvailabilityDefinition availability) {
        this.availability = availability;
        return (T)this;
    }
    abstract public ActionDefinition definition();
}
