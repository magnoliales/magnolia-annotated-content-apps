package com.magnoliales.annotatedapp.field;

import com.magnoliales.annotatedapp.UI;
import info.magnolia.ui.form.config.SelectFieldBuilder;
import info.magnolia.ui.form.field.definition.FieldDefinition;

import java.lang.reflect.Field;
import java.util.Arrays;

public class SelectFieldGenerator extends AbstractAnnotatedFieldGenerator<UI.Dialog.SelectField> {

    public SelectFieldGenerator() {
        super(UI.Dialog.SelectField.class);
    }

    @Override
    protected FieldDefinition doGenerateFieldDefinition(Field field, UI.Dialog.SelectField annotation) {
        return new SelectFieldBuilder(field.getName())
                .options(Arrays.asList(annotation.value()))
                .defaultValue(annotation.value()[0])
                .definition();
    }
}
