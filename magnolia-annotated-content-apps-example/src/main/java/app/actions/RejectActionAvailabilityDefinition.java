package app.actions;

import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.ui.api.availability.*;
import info.magnolia.ui.vaadin.integration.jcr.JcrNodeItemId;
import nodes.Comment;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RejectActionAvailabilityDefinition extends ConfiguredAvailabilityDefinition {

    public RejectActionAvailabilityDefinition() {
        ConfiguredAvailabilityRuleDefinition definition = new ConfiguredAvailabilityRuleDefinition();
        definition.setImplementationClass(RejectAvailabilityRule.class);
        List<AvailabilityRuleDefinition> rules = new ArrayList<>();
        rules.add(definition);
        setRules(rules);
    }

    public static class RejectAvailabilityRule implements AvailabilityRule {

        @Override
        public boolean isAvailable(Collection<?> itemIds) {
            for (Object itemId : itemIds) {
                if (itemId instanceof JcrNodeItemId) {
                    String workspace = ((JcrNodeItemId) itemId).getWorkspace();
                    String uuid = ((JcrNodeItemId) itemId).getUuid();
                    try {
                        Node node = NodeUtil.getNodeByIdentifier(workspace, uuid);
                        if (!PropertyUtil.getBoolean(node, Comment.APPROVED_FIELD, true)) {
                            return false;
                        }
                    } catch (RepositoryException e) {
                        throw new RuntimeException("Cannot check rule availability on node", e);
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
    }
}

