package com.magnoliales.annotatedapp.field;

import com.magnoliales.annotatedapp.UI;
import info.magnolia.ui.form.field.definition.CheckboxFieldDefinition;
import info.magnolia.ui.form.field.definition.FieldDefinition;

import java.lang.reflect.Field;

public class CheckboxFieldBuilder extends AbstractAnnotatedFieldBuilder<UI.Dialog.CheckboxField> {

    public CheckboxFieldBuilder() {
        super(UI.Dialog.CheckboxField.class);
    }

    @Override
    protected FieldDefinition doBuildFieldDefinition(Field field, UI.Dialog.CheckboxField annotation) {
        CheckboxFieldDefinition fieldDefinition = new info.magnolia.ui.form.config.CheckboxFieldBuilder(field.getName()).definition();
        // Need to set the default value otherwise if the field is not selected the 'false' value is not stored
        fieldDefinition.setDefaultValue(annotation.defaultValue() ? "true" : "false");
        return fieldDefinition;
    }
}
