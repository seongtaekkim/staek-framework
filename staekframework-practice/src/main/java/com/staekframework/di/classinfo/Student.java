package com.staekframework.di.classinfo;


// javap -c -v 에서 에노테이션 정보가 안보임..
@StudentAnnotation
public class Student implements Person {
    @StudentTypeAnnotation
    protected String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
