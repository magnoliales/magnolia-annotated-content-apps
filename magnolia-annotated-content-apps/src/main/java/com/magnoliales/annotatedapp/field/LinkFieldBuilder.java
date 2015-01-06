package com.magnoliales.annotatedapp.field;

import com.magnoliales.annotatedapp.UI;
import info.magnolia.ui.form.field.converter.BaseIdentifierToPathConverter;
import info.magnolia.ui.form.field.definition.FieldDefinition;

import java.lang.reflect.Field;
import java.util.Arrays;

public class LinkFieldBuilder extends AbstractAnnotatedFieldBuilder<UI.Dialog.LinkField> {

    public LinkFieldBuilder() {
        super(UI.Dialog.LinkField.class);
    }

    @Override
    protected FieldDefinition doBuildFieldDefinition(Field field, UI.Dialog.LinkField annotation) {
        return new info.magnolia.ui.form.config.LinkFieldBuilder(field.getName())
                .appName("pages")
                .targetWorkspace("website")
                .identifierToPathConverter(new BaseIdentifierToPathConverter())
                .definition();
    }
}