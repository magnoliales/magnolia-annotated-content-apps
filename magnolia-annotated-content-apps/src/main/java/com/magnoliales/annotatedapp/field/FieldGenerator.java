package com.magnoliales.annotatedapp.field;

import info.magnolia.ui.form.field.definition.FieldDefinition;

import java.lang.reflect.Field;

public interface FieldGenerator {

    FieldDefinition generateFieldDefinition(Field field);
}
