package com.staekframework.test;

import com.staekframework.di.Repository;
import com.staekframework.di.WireInject;

@Repository
public class RepositoryClass {
    Datasource datasource;

    public RepositoryClass(Datasource datasource) {
        this.datasource = datasource;
    }
}
