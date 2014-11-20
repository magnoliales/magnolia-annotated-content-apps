package com.magnoliales.annotatedapp.task;

import com.magnoliales.annotatedapp.AnnotatedContentAppsAppDescriptor;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.module.InstallContext;
import info.magnolia.module.delta.AbstractRepositoryTask;
import info.magnolia.module.delta.TaskExecutionException;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

public class AnnotatedContentAppInstallTask extends AbstractRepositoryTask {

    protected Class<? extends AnnotatedContentAppsAppDescriptor> appClass;
    protected String appName;
    protected String appGroup;

    public AnnotatedContentAppInstallTask(Class<? extends AnnotatedContentAppsAppDescriptor> appClass, String appGroup) {
        this(appClass.getSimpleName(), appGroup, appClass);
    }

    public AnnotatedContentAppInstallTask(String appName, String appGroup, Class<? extends AnnotatedContentAppsAppDescriptor> appClass) {
        super(
                String.format("Install '%s'", appClass.getCanonicalName()),
                String.format("Installing content app named of class '%s' under name '%s'.", appClass.getCanonicalName(), appName)
        );
        this.appName = appName;
        this.appGroup = appGroup;
        this.appClass = appClass;
    }

    @Override
    protected void doExecute(InstallContext installContext) throws RepositoryException, TaskExecutionException {
        Node moduleNode = installContext.getOrCreateCurrentModuleNode().getJCRNode();
        Node appsNode = NodeUtil.createPath(moduleNode,"apps", NodeTypes.Content.NAME);
        Node appNode = NodeUtil.createPath(appsNode, appName, NodeTypes.ContentNode.NAME);
        appNode.setProperty("class",appClass.getCanonicalName());
        addAppToLauncherLayout(installContext.getConfigJCRSession());
    }

    protected void addAppToLauncherLayout(Session configJCRSession) throws RepositoryException {
        Node configNode = configJCRSession.getNode("/modules/ui-admincentral/config");
        NodeUtil.createPath(configNode, "appLauncherLayout/groups/" + appGroup + "/apps/" + appName, NodeTypes.ContentNode.NAME);
    }
}
