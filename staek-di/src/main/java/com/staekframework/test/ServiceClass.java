package com.staekframework.test;


import com.staekframework.di.Inject;
import com.staekframework.di.WireInject;

@Inject
public class ServiceClass {


    @WireInject
    RepositoryClass repositoryClass; // package-private

    public RepositoryClass getRepositoryClass() {
        return repositoryClass;
    }
}
