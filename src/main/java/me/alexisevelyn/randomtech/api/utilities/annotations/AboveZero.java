package me.alexisevelyn.randomtech.api.utilities.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.TYPE_USE})
public @interface AboveZero {
    // TODO: Figure out how to check if number is 0 or below
    float value() default 1.0F;

    Class<? extends Exception> exception() default Exception.class;
}
