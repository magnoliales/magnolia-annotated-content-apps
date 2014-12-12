package nodes;

import com.magnoliales.annotatedapp.UI;
import com.magnoliales.annotatedapp.column.LastModifiedColumnBuilder;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Field;
import org.apache.jackrabbit.ocm.mapper.impl.annotation.Node;

@Node(jcrType = "contact", discriminator = false)
@UI.App(name = "contacts",
        workspace = "contacts",
        columns = {
                @UI.Presenter.Column(name = "name", property = "name"),
                @UI.Presenter.Column(name = "surname", property = "surname"),
                @UI.Presenter.Column(name = "lastModified", builder = LastModifiedColumnBuilder.class)
        }
)
public class Contact {

    @Field(jcrName = "jcr:uuid")
    private String id;

    @Field
    @UI.Dialog.TextField
    private String name;

    @Field
    @UI.Dialog.TextField
    private String surname;

    @Field
    @UI.Dialog.CheckboxField(defaultValue = false)
    private Boolean favourite;

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

    public Boolean getFavourite() { return favourite; }

    public void setFavourite(Boolean favourite) { this.favourite = favourite; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

}
