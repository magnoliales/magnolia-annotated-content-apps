package com.magnoliales.annotatedapp.field;

import com.magnoliales.annotatedapp.UI;
import info.magnolia.ui.form.config.TextFieldBuilder;
import info.magnolia.ui.form.field.definition.FieldDefinition;
import java.lang.reflect.Field;

public class TextFieldGenerator extends AnnotatedFieldGenerator<UI.Dialog.TextField> {

    public TextFieldGenerator() {
        super(UI.Dialog.TextField.class);
    }

    @Override
    protected FieldDefinition doGenerateFieldDefinition(Field field, UI.Dialog.TextField annotation) {
        return new TextFieldBuilder(field.getName())
                .rows(Integer.parseInt(annotation.rows()))
                .definition();
    }
}
