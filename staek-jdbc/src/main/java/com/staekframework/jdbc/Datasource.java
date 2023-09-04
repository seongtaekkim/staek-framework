package com.staekframework.jdbc;

import java.sql.Connection;

public interface Datasource {
    public Connection newConnection();
}
