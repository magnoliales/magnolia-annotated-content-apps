package setup;


import app.ExampleAnnotatedAppAppDescriptor;
import com.magnoliales.annotatedapp.task.AnnotatedContentAppInstallTask;
import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.Task;

import java.util.ArrayList;
import java.util.List;

public class ExampleAnnotatedContentAppVersionHandler extends DefaultModuleVersionHandler {

    @Override
    protected List<Task> getExtraInstallTasks(InstallContext installContext) {
        List<Task> tasks = new ArrayList<Task>();
        tasks.add(new AnnotatedContentAppInstallTask("contacts", "edit", ExampleAnnotatedAppAppDescriptor.class));
        return tasks;
    }

}
