package nodes;

import com.magnoliales.annotatedapp.UI;
import com.magnoliales.annotatedapp.actions.EditActionDefinitionFactory;
import com.magnoliales.annotatedapp.column.LastModifiedColumnBuilder;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Field;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;

@Node(jcrType = "comment", discriminator = false)
@UI.App(name = "Comments",
        workspace = "comments",
        columns = {
                @UI.Presenter.Column(name = "name", property = "name"),
                @UI.Presenter.Column(name = "content", property = "content"),
                @UI.Presenter.Column(name = "lastModified", builder = LastModifiedColumnBuilder.class)
        },
        actions = {
                EditActionDefinitionFactory.class
        }
)
public class Comment {

    @Field(jcrName = "jcr:uuid")
    private String id;

    @Field
    @UI.Dialog.TextField
    private String name;

    @Field
    @UI.Dialog.TextField(rows = "5")
    private String content;

    @Field
    private String pageId; // <- add magnolia resolver to connect to elements in different workspace

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }
}