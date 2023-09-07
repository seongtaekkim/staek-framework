package com.staekframework.jdbc;

import com.staekframework.jdbc.yaml.InitYaml;
import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.SQLException;


public class SQLITE_JDBC implements Datasource {

	private static final String URL = InitYaml.get().getValue("SQLITE_JDBC.URL");

	@Override
	public Connection newConnection() {
		String url = URL;

		SQLiteConfig config = new SQLiteConfig();
		Connection conn = null;
		try {
			conn = org.sqlite.JDBC.createConnection(url, config.toProperties());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return conn;

	}
	
}
