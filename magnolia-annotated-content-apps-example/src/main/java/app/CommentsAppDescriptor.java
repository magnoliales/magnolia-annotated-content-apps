package app;

import com.magnoliales.annotatedapp.AnnotatedContentAppsAppDescriptor;
import com.magnoliales.annotatedapp.constraint.AnnotatedDropConstraint;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import nodes.Comment;

public class CommentsAppDescriptor extends AnnotatedContentAppsAppDescriptor {

    public CommentsAppDescriptor() {
        super(Comment.class, CommentsDropConstraint.class);
    }

    public static class CommentsDropConstraint extends AnnotatedDropConstraint {

        public CommentsDropConstraint() {
            super(Comment.class);
        }
    }

    public static class ApproveActionDefinition extends ConfiguredActionDefinition {
        public ApproveActionDefinition() {
            setName("approve");
            setIcon("icon-tick");
        }
    }

    public static class RejectActionDefinition extends ConfiguredActionDefinition {
        public RejectActionDefinition() {
            setName("reject");
            setIcon("icon-close");
            // initialize definition
        }
    }
}
