package com.staekframework.di.classinfo;


import java.lang.annotation.*;

/**
 * ElementType.TYPE : interface, class, enum
 * ElementType.FIELD : field
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Inherited // 애노테이션 사용하는 리소스를 상속받는 부분도 포함한다.
public @interface StudentAnnotation {

    String value() default "staek";
    int number() default 10;
}
