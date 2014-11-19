package com.magnoliales.annotatedapp.field;

import info.magnolia.ui.form.field.definition.FieldDefinition;

import java.lang.reflect.Field;

public interface FieldBuilder {

    FieldDefinition buildFieldDefinition(Field field);
}
