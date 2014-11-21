package app;

import com.magnoliales.annotatedapp.AnnotatedContentAppsAppDescriptor;
import com.magnoliales.annotatedapp.constraint.AnnotatedDropConstraint;
import nodes.Contact;

public class ExampleAnnotatedAppAppDescriptor extends AnnotatedContentAppsAppDescriptor {

    public ExampleAnnotatedAppAppDescriptor() {
        super(Contact.class, ContactDropConstraint.class);
    }

    public static class ContactDropConstraint extends AnnotatedDropConstraint {
        public ContactDropConstraint() {
            super(Contact.class);
        }
    }

}