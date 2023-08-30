package com.staekframework.di.classinfo;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface StudentTypeAnnotation {
    int type() default 100;
}
