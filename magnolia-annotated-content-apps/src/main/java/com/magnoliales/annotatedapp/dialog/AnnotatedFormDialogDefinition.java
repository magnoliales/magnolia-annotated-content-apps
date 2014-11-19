package com.magnoliales.annotatedapp.dialog;

import com.magnoliales.annotatedapp.UI;
import com.magnoliales.annotatedapp.field.FieldGenerator;
import info.magnolia.ui.admincentral.dialog.action.CancelDialogActionDefinition;
import info.magnolia.ui.admincentral.dialog.action.SaveDialogActionDefinition;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.dialog.definition.ConfiguredFormDialogDefinition;
import info.magnolia.ui.form.config.CheckboxFieldBuilder;
import info.magnolia.ui.form.config.SelectFieldBuilder;
import info.magnolia.ui.form.config.TextFieldBuilder;
import info.magnolia.ui.form.definition.ConfiguredFormDefinition;
import info.magnolia.ui.form.definition.ConfiguredTabDefinition;
import info.magnolia.ui.form.field.definition.CheckboxFieldDefinition;
import info.magnolia.ui.form.field.definition.FieldDefinition;
import info.magnolia.ui.form.field.definition.SelectFieldDefinition;
import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AnnotatedFormDialogDefinition extends ConfiguredFormDialogDefinition {

    private static final Logger log = LoggerFactory.getLogger(ConfiguredFormDialogDefinition.class);

    protected Class<?> nodeClass;

    public Class<?> getNodeClass() {
        return nodeClass;
    }

    public AnnotatedFormDialogDefinition(Class<?> nodeClass) {

        this.nodeClass = nodeClass;
        this.setId(WordUtils.uncapitalize(nodeClass.getSimpleName()));

        ConfiguredFormDefinition form = new ConfiguredFormDefinition();
        ConfiguredTabDefinition tab = new ConfiguredTabDefinition();
        tab.setName("mainTab");

        for (Field field : nodeClass.getDeclaredFields()) {
            Class<? extends FieldGenerator> fieldGeneratorClass = null;
            if(field.isAnnotationPresent(UI.Dialog.Field.class)) {
                fieldGeneratorClass = field.getAnnotation(UI.Dialog.Field.class).implementation();
            } else if(field.isAnnotationPresent(UI.Dialog.CheckboxField.class)) {
                fieldGeneratorClass = field.getAnnotation(UI.Dialog.CheckboxField.class).implementation();
            } else if (field.isAnnotationPresent(UI.Dialog.SelectField.class)) {
                fieldGeneratorClass = field.getAnnotation(UI.Dialog.SelectField.class).implementation();
            } else if (field.isAnnotationPresent(UI.Dialog.TextField.class)) {
                fieldGeneratorClass = field.getAnnotation(UI.Dialog.TextField.class).implementation();
            }
            if(fieldGeneratorClass != null) {
                try {
                    FieldGenerator fieldGenerator = fieldGeneratorClass.newInstance();
                    tab.addField(fieldGenerator.generateFieldDefinition(field));
                } catch (InstantiationException e) {
                    log.error("Could not create a new instance of '" + fieldGeneratorClass.getCanonicalName() + "', please ensure it has a 0 argument constructor");
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    log.error("Could not create a new instance of '" + fieldGeneratorClass.getCanonicalName() + "', please ensure it has a 0 argument constructor");
                    e.printStackTrace();
                }
            }
        }

        form.addTab(tab);
        setForm(form);

        Map<String, ActionDefinition> actions = new HashMap<String, ActionDefinition>();
        //actions.put("save", getSaveAction());
        actions.put("commit", getCommitAction());
        actions.put("cancel", getCancelAction());
        setActions(actions);
    }

    private ActionDefinition getSaveAction() {
        SaveDialogActionDefinition actionDefinition = new SaveDialogActionDefinition();
        actionDefinition.setName("save");
        return actionDefinition;
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
