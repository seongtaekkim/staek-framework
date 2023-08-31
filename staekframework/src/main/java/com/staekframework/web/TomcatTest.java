package com.staekframework.web;

import com.staekframework.di.ScanAndNewInstance;
import com.staekframework.test.RepositoryClass;
import com.staekframework.test.ServiceClass;
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

		/**
		 * TODO Tomat jar 를 이용해서 서버를 띄운다.
		 */
		/*
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
		 */


		/**
		 * TODO staek-di.jar 를 이용해서 생성한 인스턴스를 가져올 수 있다.
		 */
		RepositoryClass object = ScanAndNewInstance.getObject(RepositoryClass.class);
		System.out.println(object);
		ServiceClass object1 = ScanAndNewInstance.getObject(ServiceClass.class);
		System.out.println(object1);

	}
}
