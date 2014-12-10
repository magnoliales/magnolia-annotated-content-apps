package com.magnoliales.annotatedapp.field;

import info.magnolia.ui.dialog.definition.ConfiguredFormDialogDefinition;
import info.magnolia.ui.form.field.definition.FieldDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public abstract class AbstractAnnotatedFieldBuilder<T extends Annotation> implements FieldBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguredFormDialogDefinition.class);

    private Class<T> annotationClass;

    public AbstractAnnotatedFieldBuilder(Class<T> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public FieldDefinition buildFieldDefinition(Field field) {
        if (field.isAnnotationPresent(annotationClass)) {
            return doBuildFieldDefinition(field, field.getAnnotation(annotationClass));
        } else {
            LOGGER.error("Field '"
                    + field.getName()
                    + "' from class '"
                    + field.getDeclaringClass().getCanonicalName()
                    + "'does not have required annotation of class '"
                    + annotationClass.getCanonicalName() + "'");
            return null;
        }
    }

    protected abstract FieldDefinition doBuildFieldDefinition(Field field, T annotation);
}
