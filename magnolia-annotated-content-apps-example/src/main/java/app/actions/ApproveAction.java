package app.actions;

import info.magnolia.event.EventBus;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.ui.api.action.AbstractAction;
import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.api.event.AdmincentralEventBus;
import info.magnolia.ui.api.event.ContentChangedEvent;
import info.magnolia.ui.vaadin.integration.jcr.AbstractJcrNodeAdapter;
import nodes.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class ApproveAction extends AbstractAction<ApproveActionDefinition> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewPageAction.class);

    private AbstractJcrNodeAdapter nodeAdapter;
    private EventBus admincentralEventBus;

    @Inject
    public ApproveAction(ApproveActionDefinition definition, AbstractJcrNodeAdapter nodeAdapter,
                         @Named(AdmincentralEventBus.NAME) EventBus admincentralEventBus) {
        super(definition);
        this.nodeAdapter = nodeAdapter;
        this.admincentralEventBus = admincentralEventBus;
    }

    @Override
    public void execute() throws ActionExecutionException {
        try {
            Node commentNode = nodeAdapter.getJcrItem();
            PropertyUtil.setProperty(commentNode, Comment.APPROVED_FIELD, Boolean.TRUE);
            commentNode.getSession().save();
            admincentralEventBus.fireEvent(new ContentChangedEvent(nodeAdapter.getItemId()));
        } catch (RepositoryException e) {
            throw new ActionExecutionException(e);
        }
    }
}
