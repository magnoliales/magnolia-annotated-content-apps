package setup;

import app.CommentsAppDescriptor;
import app.ContactsAppDescriptor;
import com.magnoliales.annotatedapp.task.AnnotatedContentAppInstallTask;
import info.magnolia.context.MgnlContext;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExampleAnnotatedContentAppVersionHandler extends DefaultModuleVersionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleAnnotatedContentAppVersionHandler.class);

    @Override
    protected List<Task> getExtraInstallTasks(InstallContext installContext) {
        List<Task> tasks = new ArrayList<>();
        tasks.add(new AnnotatedContentAppInstallTask("contacts", "edit", ContactsAppDescriptor.class));
        tasks.add(new AnnotatedContentAppInstallTask("comments", "edit", CommentsAppDescriptor.class));
        return tasks;
    }
}
