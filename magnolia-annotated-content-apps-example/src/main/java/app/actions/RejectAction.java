package app.actions;

import info.magnolia.event.EventBus;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.ui.api.action.AbstractAction;
import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.api.event.AdmincentralEventBus;
import info.magnolia.ui.api.event.ContentChangedEvent;
import info.magnolia.ui.api.location.LocationController;
import info.magnolia.ui.vaadin.integration.jcr.AbstractJcrNodeAdapter;
import nodes.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class RejectAction extends AbstractAction<RejectActionDefinition> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewPageAction.class);

    private AbstractJcrNodeAdapter nodeAdapter;
    private EventBus admincentralEventBus;

    @Inject
    public RejectAction(RejectActionDefinition definition, AbstractJcrNodeAdapter nodeAdapter,
                        @Named(AdmincentralEventBus.NAME) EventBus admincentralEventBus) {
        super(definition);
        this.nodeAdapter = nodeAdapter;
        this.admincentralEventBus = admincentralEventBus;
    }

    @Override
    public void execute() throws ActionExecutionException {
        try {
            Node commentNode = nodeAdapter.getJcrItem();
            PropertyUtil.setProperty(commentNode, Comment.APPROVED_FIELD, Boolean.FALSE);
            commentNode.getSession().save();
            admincentralEventBus.fireEvent(new ContentChangedEvent(nodeAdapter.getItemId()));
        } catch (RepositoryException e) {
            throw new ActionExecutionException(e);
        }
    }
}