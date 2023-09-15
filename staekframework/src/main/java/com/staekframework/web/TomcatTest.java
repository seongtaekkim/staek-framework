package com.staekframework.web;

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
		 * TODO Tomat jar 를 이용해서 웹서버를 실행한다.
		 */
		String root = new File(".").getAbsolutePath();

		String baseDir = root + File.separatorChar + "temp";

		int webPort = 18080;

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(webPort);

		tomcat.setBaseDir(baseDir);
		tomcat.setPort(webPort);

//		Context context = tomcat.addContext("/", baseDir);

		/**
		 * 임베디드 톰캣을 서버로 사용하였음
		 * - 우선은 하드코딩으로 servlet 자원과 매핑코드를 작성했으나
		 * - di를 이용해서 주입을 하는것으로 개선할 예정이다.
		 */
//		UserServlet userServlet = new UserServlet();
//		tomcat.addServlet("/", "user", userServlet);
//		context.addServletMappingDecoded("/user", "user");

		tomcat.addWebapp("", "/Users/staek/Documents/staek-framework/staekframework/web");

//		String characterSet = init.getCharacterSet();
//		System.out.println("uri characterSet [" + characterSet + "]");
//		Connector conn = tomcat.getConnector();
//		conn.setURIEncoding(characterSet);

		tomcat.start();
		System.out.println("started tomcat server");
		tomcat.getServer().await(); // 요청대기


		/**
		 * TODO staek-di.jar 를 이용해서 생성한 인스턴스를 가져올 수 있다.
		 */
//		RepositoryClass object = ScanAndNewInstance.getObject(RepositoryClass.class);
//		System.out.println(object);
//		ServiceClass object1 = ScanAndNewInstance.getObject(ServiceClass.class);
//		System.out.println(object1);

	}
}
