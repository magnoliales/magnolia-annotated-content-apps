package com.magnoliales.annotatedapp.dialog;

import com.magnoliales.annotatedapp.UI;
import com.magnoliales.annotatedapp.field.FieldBuilder;
import info.magnolia.ui.admincentral.dialog.action.CancelDialogActionDefinition;
import info.magnolia.ui.admincentral.dialog.action.SaveDialogActionDefinition;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.dialog.definition.ConfiguredFormDialogDefinition;
import info.magnolia.ui.form.definition.ConfiguredFormDefinition;
import info.magnolia.ui.form.definition.ConfiguredTabDefinition;
import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

enum AnnotatedFormDialogDefinitionFactory {

    INSTANCE;

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotatedFormDialogDefinitionFactory.class);

    public ConfiguredFormDialogDefinition createFormDialogDefinition(Class<?> nodeClass) {

        ConfiguredFormDialogDefinition definition = new ConfiguredFormDialogDefinition();
        definition.setId(WordUtils.uncapitalize(nodeClass.getSimpleName()));

        ConfiguredFormDefinition form = new ConfiguredFormDefinition();
        ConfiguredTabDefinition tab = new ConfiguredTabDefinition();
        tab.setName("mainTab");

        for (Field field : nodeClass.getDeclaredFields()) {
            Class<? extends FieldBuilder> fieldGeneratorClass = null;
            if (field.isAnnotationPresent(UI.Dialog.Field.class)) {
                fieldGeneratorClass = field.getAnnotation(UI.Dialog.Field.class).value();
            } else if (field.isAnnotationPresent(UI.Dialog.CheckboxField.class)) {
                fieldGeneratorClass = field.getAnnotation(UI.Dialog.CheckboxField.class).implementation();
            } else if (field.isAnnotationPresent(UI.Dialog.SelectField.class)) {
                fieldGeneratorClass = field.getAnnotation(UI.Dialog.SelectField.class).implementation();
            } else if (field.isAnnotationPresent(UI.Dialog.TextField.class)) {
                fieldGeneratorClass = field.getAnnotation(UI.Dialog.TextField.class).implementation();
            } else if (field.isAnnotationPresent(UI.Dialog.LinkField.class)) {
                fieldGeneratorClass = field.getAnnotation(UI.Dialog.LinkField.class).implementation();
            }
            if (fieldGeneratorClass != null) {
                try {
                    FieldBuilder fieldBuilder = fieldGeneratorClass.newInstance();
                    tab.addField(fieldBuilder.buildFieldDefinition(field));
                } catch (InstantiationException | IllegalAccessException e) {
                    LOGGER.error("Could not create a new instance of '"
                            + fieldGeneratorClass.getCanonicalName()
                            + "', please ensure it has a 0 argument constructor");
                    e.printStackTrace();
                }
            }
        }

        form.addTab(tab);
        definition.setForm(form);

        Map<String, ActionDefinition> actions = new HashMap<>();
        actions.put("commit", getCommitAction());
        actions.put("cancel", getCancelAction());
        definition.setActions(actions);
        return definition;
    }

    private ActionDefinition getCommitAction() {
        SaveDialogActionDefinition actionDefinition = new SaveDialogActionDefinition();
        actionDefinition.setName("commit");
        return actionDefinition;
    }

    private ActionDefinition getCancelAction() {
        CancelDialogActionDefinition actionDefinition = new CancelDialogActionDefinition();
        actionDefinition.setName("cancel");
        return actionDefinition;
    }
}
