package com.magnoliales.annotatedapp;

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

    @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.TYPE})
    public @interface NoActivation{}

    @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.TYPE})
    public @interface App {
        public String name();
        public String workspace();
        public String theme() default "";
        public Presenter.Column[] columns() default {};
    }

    public interface Presenter {

        @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.TYPE})
        public @interface Column {
            public String name();
            public String property() default "jcrName";
            public boolean editable() default false;
            public boolean sortable() default false;
            public int width() default -1;
            public float expandRatio() default (float)1.0;
            public Class<? extends AbstractColumnBuilder> builder() default PropertyColumnBuilder.class;
        }
    }

    public interface Dialog {

        @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD})
        public @interface Field {
            public Class<? extends FieldBuilder> value();
        }

        @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD})
        public @interface TextField {
            public String rows() default "1";
            public Class<? extends FieldBuilder> implementation() default TextFieldBuilder.class;
        }

        @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD})
        public @interface CheckboxField {
            public boolean defaultValue() default false;
            public Class<? extends FieldBuilder> implementation() default CheckboxFieldBuilder.class;
        }

        @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD})
        public @interface SelectField {
            public String[] value() default {};
            public Class<? extends FieldBuilder> implementation() default SelectFieldBuilder.class;
        }
    }

    @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD})
    public @interface ChildNodes {
        public Class<?> childClassName() default Object.class;
    }
}
