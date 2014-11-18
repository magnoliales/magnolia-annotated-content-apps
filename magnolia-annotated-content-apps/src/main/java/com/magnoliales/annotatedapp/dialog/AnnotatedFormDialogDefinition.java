package com.magnoliales.annotatedapp.dialog;

import com.magnoliales.annotatedapp.UI;
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

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AnnotatedFormDialogDefinition extends ConfiguredFormDialogDefinition {

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
            if (field.isAnnotationPresent(UI.Dialog.TextField.class)) {
                UI.Dialog.TextField annotation = field.getAnnotation(UI.Dialog.TextField.class);
                FieldDefinition fieldDefinition = new TextFieldBuilder(field.getName())
                        .rows(Integer.parseInt(annotation.rows()))
                        .definition();
                tab.addField(fieldDefinition);
            } else if (field.isAnnotationPresent(UI.Dialog.CheckboxField.class)) {
                CheckboxFieldDefinition fieldDefinition = new CheckboxFieldBuilder(field.getName()).definition();
                // Need to set the default value otherwise if the field is not selected the 'false' value is not stored
                fieldDefinition.setDefaultValue(field.getAnnotation(UI.Dialog.CheckboxField.class).defaultValue() ? "true" : "false");
                tab.addField(fieldDefinition);
            } else if (field.isAnnotationPresent(UI.Dialog.SelectField.class)) {
                UI.Dialog.SelectField annotation = field.getAnnotation(UI.Dialog.SelectField.class);
                SelectFieldDefinition fieldDefinition = new SelectFieldBuilder(field.getName())
                        .options(Arrays.asList(annotation.value()))
                        .defaultValue(annotation.value()[0])
                        .definition();
                tab.addField(fieldDefinition);
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
