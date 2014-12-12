package app;

import com.magnoliales.annotatedapp.AnnotatedContentAppsAppDescriptor;
import com.magnoliales.annotatedapp.constraint.AnnotatedDropConstraint;
import info.magnolia.ui.api.action.ActionDefinition;
import nodes.Contact;

public class ContactsAppDescriptor extends AnnotatedContentAppsAppDescriptor {

    public ContactsAppDescriptor() {
        super(Contact.class, ContactsDropConstraint.class);
    }

    public static class ContactsDropConstraint extends AnnotatedDropConstraint {
        public ContactsDropConstraint() {
            super(Contact.class);
        }
    }
}