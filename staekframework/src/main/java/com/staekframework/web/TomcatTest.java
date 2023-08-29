package com.staekframework.web;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.http.HttpServlet;
import java.io.File;

/**
 * @author seongtaek
 *
 */
public class TomcatTest {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String root = new File(".").getAbsolutePath();

		String baseDir = root + File.separatorChar + "temp";

		int webPort = 18080;

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(webPort);

		tomcat.setBaseDir(baseDir);
		tomcat.setPort(webPort);

		Context context = tomcat.addContext("/", baseDir);

		HttpServlet board = new ServletTest();

		tomcat.addServlet("/", "test", board);
		context.addServletMappingDecoded("/test", "test");

		tomcat.start();
		tomcat.getServer().await();

	}
}
