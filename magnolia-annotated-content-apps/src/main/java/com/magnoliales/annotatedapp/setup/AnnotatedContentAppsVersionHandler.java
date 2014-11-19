package com.magnoliales.annotatedapp.setup;


import info.magnolia.module.DefaultModuleVersionHandler;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.Task;

import java.util.ArrayList;
import java.util.List;

public class AnnotatedContentAppsVersionHandler extends DefaultModuleVersionHandler {

    @Override
    protected List<Task> getExtraInstallTasks(InstallContext installContext) {
        List<Task> tasks = new ArrayList<Task>();
        return tasks;
    }

    protected List<Task> getStartupTasks(InstallContext installContext) {
        List<Task> tasks = new ArrayList<Task>();
        installContext.getCurrentModuleDefinition();
        return tasks;
    }
}
