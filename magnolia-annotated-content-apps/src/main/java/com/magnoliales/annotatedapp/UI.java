package com.magnoliales.annotatedapp;

import com.magnoliales.annotatedapp.actions.AnnotatedActionDefinitions;
import com.magnoliales.annotatedapp.actions.EditActionDefinitions;
import com.magnoliales.annotatedapp.actions.ExportActionDefinitions;
import com.magnoliales.annotatedapp.column.AbstractColumnBuilder;
import com.magnoliales.annotatedapp.column.PropertyColumnBuilder;
import com.magnoliales.annotatedapp.field.CheckboxFieldBuilder;
import com.magnoliales.annotatedapp.field.FieldBuilder;
import com.magnoliales.annotatedapp.field.SelectFieldBuilder;
import com.magnoliales.annotatedapp.field.TextFieldBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface UI {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE })
    public @interface NoActivation { }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.TYPE })
    public @interface App {
        String name();
        String workspace();
        String theme() default "";
        Presenter.Column[] columns() default { };
        Class<? extends AnnotatedActionDefinitions>[] actions() default {
                EditActionDefinitions.class,
                ExportActionDefinitions.class
        };
    }

    public interface Presenter {

        @Retention(RetentionPolicy.RUNTIME)
        @Target({ ElementType.TYPE })
        public @interface Column {
            String name();
            String property() default "jcrName";
            boolean editable() default false;
            boolean sortable() default false;
            int width() default -1;
            float expandRatio() default 1.0F;
            Class<? extends AbstractColumnBuilder> builder() default PropertyColumnBuilder.class;
        }
    }

    public interface Dialog {

        @Retention(RetentionPolicy.RUNTIME)
        @Target({ ElementType.FIELD })
        public @interface Field {
            Class<? extends FieldBuilder> value();
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target({ ElementType.FIELD })
        public @interface TextField {
            String rows() default "1";
            Class<? extends FieldBuilder> implementation() default TextFieldBuilder.class;
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target({ ElementType.FIELD })
        public @interface CheckboxField {
            boolean defaultValue() default false;
            Class<? extends FieldBuilder> implementation() default CheckboxFieldBuilder.class;
        }

        @Retention(RetentionPolicy.RUNTIME)
        @Target({ ElementType.FIELD })
        public @interface SelectField {
            String[] value() default { };
            Class<? extends FieldBuilder> implementation() default SelectFieldBuilder.class;
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD })
    public @interface ChildNodes {
        Class<?> childClassName() default Object.class;
    }
}
