package com.staekframework.jdbc.yaml;

import java.io.File;
import java.net.InetAddress;
import java.util.Map;


public class InitYaml {

	private static InitYaml thisInc = new InitYaml();

	public static InitYaml get() {
		return thisInc;
	}

	private Map<String, Object> map;
	private String hostName;
	private String root;
	private static final String filename = "properties";

	private int port;
	private String webDir;
	private String webTmpDir;

	private InitYaml() {

		this.map = LoadYaml.getMap(filename);

		//Arrays.stream(this.map.entrySet().toArray()).forEach(System.out::println);

		this.hostName = "DEFAULT";

		try {
			this.hostName = InetAddress.getLocalHost().getHostName();
			if (this.hostName.contains(".")) {
				this.hostName = "DEFAULT";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
//		System.out.println("this.hostName : " + this.hostName);

		String yamlUrl = (String) map.get("YAML_URL");

		// root/web/WEB-INF/classes/init.yaml
		File file = new File(yamlUrl);
		// root/web/WEB-INF/classes -> webAppRoot
		file = file.getParentFile().getParentFile().getParentFile().getParentFile();
        this.root = file.getAbsolutePath();
		this.port = (int) map.get("PORT");

		/**
		 * 항목이 없으면 에러남. 나중에 처리해야 함
		 */
		this.webDir = (String) ((Map<String, Object>) map.get("WEB_DIR")).get("DEFAULT");
		this.webTmpDir = (String) ((Map<String, Object>)map.get("WEB_TEMP_DIR")).get("DEFAULT");
		this.webDir = replaceRootToString(this.webDir);
		this.webTmpDir = replaceRootToString(this.webTmpDir);


	}

	public int port() {
		return this.port;
	}

	public String webDir() {
		return this.webDir;
	}
	public String webTmpDir() {
		return this.webTmpDir;
	}

	private String replaceRootToString(String s) {
		if (this.root != null) {
			s = s.replace("[ROOT]", this.root);
		}
		return s;
	}

	public String getJDBC(String key) {
		String s = (String) ((Map<String, Object>)map.get("SQLITE_JDBC")).get(key);
		s = replaceRootToString(s);
		return s;
	}

}