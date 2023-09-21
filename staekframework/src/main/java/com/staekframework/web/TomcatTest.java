package com.staekframework.web;

import com.staekframework.jdbc.yaml.InitYaml;
import org.apache.catalina.startup.Tomcat;
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

		InitYaml init = InitYaml.get();
		/**
		 * TODO Tomat jar 를 이용해서 웹서버를 실행한다.
		 */
		String root = new File(".").getAbsolutePath();


		int webPort = init.port();

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(webPort);
		System.out.println("init.webTmpDir() : " + init.webTmpDir());
		tomcat.setBaseDir( init.webTmpDir());

		System.out.println("port:" + webPort);
		System.out.println("webdir: " + init.webDir());
		System.out.println("webtemp: " + init.webTmpDir());


		/**
		 * 임베디드 톰캣을 서버로 사용하였음
		 * - addContext ->  addServlet -> addServletMappingDecoded 순서로 지정하면
		 * - 해당하는 리소스에 매핑하여 접근할 수 있음
		 *
		 * - addWebapp를 설정하면 web.xml의 servlet 설정을 따라간다.
		 */
//		Context context = tomcat.addContext("/",  init.webTmpDir());
//		UserServlet userServlet = new UserServlet();
//		tomcat.addServlet("/", "user", userServlet);
//		context.addServletMappingDecoded("/user", "user");

		tomcat.addWebapp("", init.webDir());

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
