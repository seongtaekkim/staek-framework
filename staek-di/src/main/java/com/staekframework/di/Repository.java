package com.staekframework.di;

import java.lang.annotation.*;

/**
 * Data Access Object DI 용도
 *
 * 해당 annotation을 기술한 객체는 DAO로 간주하고
 * 생성자 파라메터가 1개가 있어야한다. (Datasource 를 기대한다)
 * 이미 생성한 objectMap 정보에 존재하는 지 판단 후 생성한다.
 *
 * => 전체를 뒤져야 하기 때문에 속도를 생각해 보아야 한다..
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
@Inherited
public @interface Repository {

}
