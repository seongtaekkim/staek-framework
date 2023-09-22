package com.staekframework.di;

import java.lang.annotation.*;

/**
 * 이미 존재하는 인스턴스 정보를 주입할 용도.
 *
 * method: 인자의 정보가 objectMaps에 존재할 경우 DI한 후 로직을 처리한다.
 * field: 인자의 정보가 objectMaps에에 존재할 경우 DI한다.
 * constructor: 인자의 정보가 objectMaps에 존재할 경우 DI한 후 로직을 처리한다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD})
@Inherited
public @interface WireInject {
}
