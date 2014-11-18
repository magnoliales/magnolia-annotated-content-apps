package app;

import com.magnoliales.annotatedapp.AnnotatedContentAppsAppDescriptor;
import com.magnoliales.annotatedapp.constraint.AnnotatedDropConstraint;
import com.magnoliales.annotatedapp.dialog.AnnotatedFormDialogDefinition;
import nodes.Contact;

public class ExampleAnnotatedAppAppDescriptor extends AnnotatedContentAppsAppDescriptor {

    public ExampleAnnotatedAppAppDescriptor() {
        super(Contact.class, MemberDropConstraint.class, new AnnotatedFormDialogDefinition[] {
                new MemberFormDialogDefinition()
        });
    }

    public static class MemberDropConstraint extends AnnotatedDropConstraint {
        public MemberDropConstraint() {
            super(Contact.class);
        }
    }

    public static class MemberFormDialogDefinition extends AnnotatedFormDialogDefinition {
        public MemberFormDialogDefinition() {
            super(Contact.class);
        }
    }
}