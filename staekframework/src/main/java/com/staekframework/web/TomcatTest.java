package com.staekframework.web;

import com.staekframework.di.ScanAndNewInstance;
import com.staekframework.jdbc.yaml.InitYaml;
import com.staekframework.test.RepositoryClass;
import com.staekframework.test.ServiceClass;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.yaml.snakeyaml.Yaml;

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

		InitYaml init = InitYaml.get();
		/**
		 * TODO Tomat jar 를 이용해서 웹서버를 실행한다.
		 */
		String root = new File(".").getAbsolutePath();


		int webPort = init.port();

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(webPort);

		tomcat.setBaseDir( init.webTmpDir());
		tomcat.setPort(webPort);

		Context context = tomcat.addContext("/",  init.webTmpDir());

		/**
		 * 임베디드 톰캣을 서버로 사용하였음
		 * - 우선은 하드코딩으로 servlet 자원과 매핑코드를 작성했으나
		 * - di를 이용해서 주입을 하는것으로 개선할 예정이다.
		 */
		UserServlet userServlet = new UserServlet();
		tomcat.addServlet("/", "user", userServlet);
		context.addServletMappingDecoded("/user", "user");

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
