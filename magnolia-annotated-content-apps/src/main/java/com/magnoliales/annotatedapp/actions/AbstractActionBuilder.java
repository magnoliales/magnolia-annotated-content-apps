package com.magnoliales.annotatedapp.actions;

import com.magnoliales.annotatedapp.availability.AvailabilityBuilder;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.api.availability.AvailabilityDefinition;

public abstract class AbstractActionBuilder<T> {

    private AvailabilityDefinition availability = new AvailabilityBuilder().definition();
    private String icon;

    @SuppressWarnings("unchecked")
    public T setIcon(String icon) {
        this.icon = icon;
        return (T) this;
    }

    protected String getIcon(String defaultIcon) {
        return icon == null ? defaultIcon : icon;
    }

    @SuppressWarnings("unchecked")
    public T setAvailability(AvailabilityDefinition availability) {
        this.availability = availability;
        return (T) this;
    }

    protected AvailabilityDefinition getAvailability() {
        return availability;
    }

    public abstract ActionDefinition definition();
}
