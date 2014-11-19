package com.magnoliales.annotatedapp.field;

import com.magnoliales.annotatedapp.UI;
import info.magnolia.ui.form.field.definition.FieldDefinition;

import java.lang.reflect.Field;

public class TextFieldBuilder extends AbstractAnnotatedFieldBuilder<UI.Dialog.TextField> {

    public TextFieldBuilder() {
        super(UI.Dialog.TextField.class);
    }

    @Override
    protected FieldDefinition doBuildFieldDefinition(Field field, UI.Dialog.TextField annotation) {
        return new info.magnolia.ui.form.config.TextFieldBuilder(field.getName())
                .rows(Integer.parseInt(annotation.rows()))
                .definition();
    }
}
