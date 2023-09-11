package com.staekframework.jdbc;

import com.staekframework.jdbc.yaml.InitYaml;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JDBCConnection implements Datasource {

	private static final String URL = InitYaml.get().getJDBC("URL");
	private static final String USERNAME = InitYaml.get().getJDBC("USERNAME");
	private static final String PASSWORD = InitYaml.get().getJDBC("PASSWORD");

	@Override
	public Connection newConnection() {

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return conn;

	}

}
