package app.columns;

import com.vaadin.ui.Table;
import info.magnolia.jcr.util.PropertyUtil;
import info.magnolia.ui.workbench.column.AbstractColumnFormatter;
import info.magnolia.ui.workbench.column.definition.AbstractColumnDefinition;
import nodes.Comment;

import javax.inject.Inject;
import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

public class ApprovalColumnFormatter extends AbstractColumnFormatter<AbstractColumnDefinition> {

    @Inject
    public ApprovalColumnFormatter(AbstractColumnDefinition definition) {
        super(definition);
    }

    @Override
    public Object generateCell(Table source, Object itemId, Object columnId) {
        final Item jcrItem = getJcrItem(source, itemId);
        if (jcrItem != null && jcrItem.isNode()) {
            Node node = (Node) jcrItem;
            Property approved = PropertyUtil.getPropertyOrNull(node, Comment.APPROVED_FIELD);

            StringBuilder style = new StringBuilder();
            if (approved != null) {
                style.append("icon-shape-circle activation-status");
                try {
                    if (approved.getBoolean()) {
                        style.append(" color-green");
                    } else {
                        style.append(" color-red");
                    }
                } catch (RepositoryException e) {
                    throw new RuntimeException("Cannot read approval state", e);
                }
            }
            return "<span class=\"" + style.toString() + "\"></span>";
        }
        return null;
    }
}
