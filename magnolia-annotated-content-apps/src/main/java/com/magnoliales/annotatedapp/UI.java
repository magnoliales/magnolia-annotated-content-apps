package com.magnoliales.annotatedapp;

import com.magnoliales.annotatedapp.column.AbstractColumnBuilder;
import com.magnoliales.annotatedapp.column.PropertyColumnBuilder;
import com.magnoliales.annotatedapp.field.CheckboxFieldGenerator;
import com.magnoliales.annotatedapp.field.FieldGenerator;
import com.magnoliales.annotatedapp.field.SelectFieldGenerator;
import com.magnoliales.annotatedapp.field.TextFieldGenerator;

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
        public @interface Field {
            public Class<? extends FieldGenerator> value();
        }

        @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD})
        public @interface TextField {
            public String rows() default "1";
            public Class<? extends FieldGenerator> implementation() default TextFieldGenerator.class;
        }

        @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD})
        public @interface CheckboxField {
            public boolean defaultValue() default false;
            public Class<? extends FieldGenerator> implementation() default CheckboxFieldGenerator.class;
        }

        @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD})
        public @interface SelectField {
            public String[] value() default {};
            public Class<? extends FieldGenerator> implementation() default SelectFieldGenerator.class;
        }
    }

    @Retention(RetentionPolicy.RUNTIME) @Target({ElementType.FIELD})
    public @interface ChildNodes {
        public Class<?> childClassName() default Object.class;
    }
}
