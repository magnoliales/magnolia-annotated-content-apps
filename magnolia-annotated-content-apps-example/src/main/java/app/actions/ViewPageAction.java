package app.actions;

import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.ui.api.action.AbstractAction;
import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.api.location.LocationController;
import info.magnolia.ui.contentapp.detail.DetailLocation;
import info.magnolia.ui.contentapp.detail.DetailView;
import info.magnolia.ui.vaadin.integration.jcr.AbstractJcrNodeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class ViewPageAction extends AbstractAction<ViewPageActionDefinition> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ViewPageAction.class);

    private LocationController locationController;
    private AbstractJcrNodeAdapter nodeAdapter;

    @Inject
    public ViewPageAction(ViewPageActionDefinition definition, AbstractJcrNodeAdapter nodeAdapter,
                          LocationController locationController) {
        super(definition);
        this.locationController = locationController;
        this.nodeAdapter = nodeAdapter;
    }

    @Override
    public void execute() throws ActionExecutionException {
        try {
            Node commentNode = nodeAdapter.getJcrItem();
            String pageId = PropertyUtil.getString(commentNode, "pageId");
            Node pageNode = NodeUtil.getNodeByIdentifier("website", pageId);
            if (!NodeUtil.isNodeType(pageNode, NodeTypes.Page.NAME)) {
                pageNode = NodeUtil.getNearestAncestorOfType(pageNode, NodeTypes.Page.NAME);
            }
            if (pageNode == null) {
                throw new ActionExecutionException("Not able to resolve page node from comment node "
                        + nodeAdapter.getJcrItem().getPath());
            }

            DetailLocation location = new DetailLocation("pages", "detail",
                    DetailView.ViewType.VIEW, pageNode.getPath(), "");

            locationController.goTo(location);
        } catch (RepositoryException e) {
            throw new ActionExecutionException(e);
        }
    }
}
