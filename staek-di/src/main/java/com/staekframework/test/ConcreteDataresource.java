package com.staekframework.test;

public class ConcreteDataresource implements Datasource {
    @Override
    public void connect() {
        System.out.println("connected datasource");
    }
}
