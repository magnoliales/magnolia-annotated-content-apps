package com.magnoliales.annotatedapp.field;

import com.magnoliales.annotatedapp.UI;
import info.magnolia.ui.form.field.definition.FieldDefinition;

import java.lang.reflect.Field;
import java.util.Arrays;

public class SelectFieldBuilder extends AbstractAnnotatedFieldBuilder<UI.Dialog.SelectField> {

    public SelectFieldBuilder() {
        super(UI.Dialog.SelectField.class);
    }

    @Override
    protected FieldDefinition doBuildFieldDefinition(Field field, UI.Dialog.SelectField annotation) {
        return new info.magnolia.ui.form.config.SelectFieldBuilder(field.getName())
                .options(Arrays.asList(annotation.value()))
                .defaultValue(annotation.value()[0])
                .definition();
    }
}
