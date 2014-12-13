package app;

import com.magnoliales.annotatedapp.AnnotatedContentAppDescriptor;
import com.magnoliales.annotatedapp.constraint.AnnotatedDropConstraint;
import nodes.Contact;

public class ContactsAppDescriptor extends AnnotatedContentAppDescriptor {

    public ContactsAppDescriptor() {
        super(Contact.class, ContactsDropConstraint.class);
    }

    public static class ContactsDropConstraint extends AnnotatedDropConstraint {
        public ContactsDropConstraint() {
            super(Contact.class);
        }
    }
}