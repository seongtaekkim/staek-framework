package com.staekframework.di.reflection;

public class SubClass {

    public static String field1 = "field1";
    private String field2 = "field2";

    public SubClass(String field2) {
        this.field2 = field2;
    }

    public void method1() {
        System.out.println("method1");
    }
    public String concat(String a, String b) {
        return a + b;
    }
}
