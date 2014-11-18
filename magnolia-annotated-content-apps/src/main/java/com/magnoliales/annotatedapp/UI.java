package com.magnoliales.annotatedapp;

import com.magnoliales.annotatedapp.column.AbstractColumnBuilder;
import com.magnoliales.annotatedapp.column.PropertyColumnBuilder;

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
            public Class<? extends AbstractColumnBuilder> builder() default PropertyColumnBuilder.class;
        }
    }

    public interface Dialog {

        @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD})
        public @interface TextField {
            public String rows() default "1";
        }

        @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD})
        public @interface CheckboxField {
            public boolean defaultValue() default false;
        }

        @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD})
        public @interface SelectField {
            public String[] value() default {};
        }
    }

    @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD})
    public @interface ChildNodes {
        public Class<?> childClassName() default Object.class;
    }
}
