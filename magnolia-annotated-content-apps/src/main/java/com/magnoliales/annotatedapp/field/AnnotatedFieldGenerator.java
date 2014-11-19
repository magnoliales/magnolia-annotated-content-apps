package com.magnoliales.annotatedapp.field;

import info.magnolia.ui.dialog.definition.ConfiguredFormDialogDefinition;
import info.magnolia.ui.form.field.definition.FieldDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public abstract class AnnotatedFieldGenerator<T extends Annotation> implements FieldGenerator {

    private static final Logger log = LoggerFactory.getLogger(ConfiguredFormDialogDefinition.class);

    protected Class<T> annotationClass;

    public AnnotatedFieldGenerator(Class<T> annotationClass ) {
        this.annotationClass = annotationClass;
    }

    public FieldDefinition generateFieldDefinition(Field field) {

        if (field.isAnnotationPresent(annotationClass)) {
            return doGenerateFieldDefinition(field, field.getAnnotation(annotationClass));
        } else {
            log.error("Field '" + field.getName() +"' from class '" + field.getDeclaringClass().getCanonicalName() + "'does not have required annotation of class '" + annotationClass.getCanonicalName() + "'" );
            return null;
        }
    }

    protected abstract FieldDefinition doGenerateFieldDefinition(Field field, T annotation );

}
