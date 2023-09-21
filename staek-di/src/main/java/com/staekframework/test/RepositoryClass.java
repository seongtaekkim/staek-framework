package com.staekframework.test;

import com.staekframework.di.Repository;
import com.staekframework.di.WireInject;

@Repository
public class RepositoryClass {
    Datasource datasource;

    @WireInject
    public RepositoryClass(Datasource datasource) {
        this.datasource = datasource;
    }
}
