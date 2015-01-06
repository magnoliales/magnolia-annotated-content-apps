package app.columns;

import com.magnoliales.annotatedapp.column.AbstractColumnBuilder;
import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.column.definition.PropertyColumnDefinition;
import nodes.Comment;

public class ApprovalColumnBuilder extends AbstractColumnBuilder<ApprovalColumnBuilder> {

    private static final int DEFAULT_WIDTH = 50;

    @Override
    public ColumnDefinition definition() {
        PropertyColumnDefinition columnDefinition = new PropertyColumnDefinition();
        columnDefinition.setName(getName());
        columnDefinition.setPropertyName(Comment.APPROVED_FIELD);
        columnDefinition.setFormatterClass(ApprovalColumnFormatter.class);
        columnDefinition.setWidth(getWidth(DEFAULT_WIDTH));
        columnDefinition.setSortable(isSortable());
        return columnDefinition;
    }
}
