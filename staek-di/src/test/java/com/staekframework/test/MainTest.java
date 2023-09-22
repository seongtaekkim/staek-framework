package com.staekframework.test;

import com.staekframework.di.ScanAndNewInstance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * DaoFactory DI test
 */
class MainTest {

    Datasource ds;

    @BeforeEach
    public void createDatasource() {
        ds = new ConcreteDataresource();
        assertThat(ds).isNotNull();
    }

    @Test
    public void createInstance() {
        ScanAndNewInstance scan = new ScanAndNewInstance();
        scan.putInstance(ds);
        assertThat(scan.size()).isEqualTo(1);
    }

    @Test
    public void getInstance() {
        ScanAndNewInstance scan = new ScanAndNewInstance();
        scan.putInstance(ds);
        assertNotNull(scan.getInstance(ConcreteDataresource.class.getName()));
    }
    @Test
    public void throws_getInstance() {
        ScanAndNewInstance scan = new ScanAndNewInstance();
        scan.putInstance(ds);
        assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                scan.getInstance(String.class.getName());
            }
        });
    }

    @Test
    public void throws_non_parameter_instance_constructor() {
        ScanAndNewInstance scan = new ScanAndNewInstance();
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                scan.scanAndCreateInstance();
            }
        });
    }

    @Test
    public void must_not_exception() {
        ScanAndNewInstance scan = new ScanAndNewInstance();
        scan.putInstance(ds);
        assertDoesNotThrow(() -> {
            scan.scanAndCreateInstance();
        });
    }
}