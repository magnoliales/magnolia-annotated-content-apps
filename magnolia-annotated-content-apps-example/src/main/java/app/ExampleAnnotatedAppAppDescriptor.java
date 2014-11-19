package app;

import com.magnoliales.annotatedapp.AnnotatedContentAppsAppDescriptor;
import com.magnoliales.annotatedapp.constraint.AnnotatedDropConstraint;
import com.magnoliales.annotatedapp.dialog.AnnotatedFormDialogDefinition;
import nodes.Contact;

public class ExampleAnnotatedAppAppDescriptor extends AnnotatedContentAppsAppDescriptor {

    public ExampleAnnotatedAppAppDescriptor() {
        super(Contact.class, ContactDropConstraint.class, new AnnotatedFormDialogDefinition[] {
                new ContactFormDialogDefinition()
        });
    }

    public static class ContactDropConstraint extends AnnotatedDropConstraint {
        public ContactDropConstraint() {
            super(Contact.class);
        }
    }

    public static class ContactFormDialogDefinition extends AnnotatedFormDialogDefinition {
        public ContactFormDialogDefinition() {
            super(Contact.class);
        }
    }
}