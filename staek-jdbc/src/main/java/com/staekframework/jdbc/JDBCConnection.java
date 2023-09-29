package com.staekframework.jdbc;

import com.staekframework.jdbc.yaml.InitYaml;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JDBCConnection implements Datasource {

	private static final String URL = InitYaml.get().getJDBC("URL");
	private static final String USERNAME = InitYaml.get().getJDBC("USERNAME");
	private static final String PASSWORD = InitYaml.get().getJDBC("PASSWORD");

	private static Connection conn = null;

	@Override
	public Connection newConnection() {

		if (conn != null)
			return conn;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return conn;

	}

	/**
	 * @deprecated 테스트 위해서 임시
	 * @return
	 */
	@Override
	public Connection getConnection() {
		return this.conn;
	}

}
