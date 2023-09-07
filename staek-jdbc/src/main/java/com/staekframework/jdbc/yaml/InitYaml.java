package com.staekframework.jdbc.yaml;

import java.io.File;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Map;


public class InitYaml {

	private static InitYaml thisInc = new InitYaml();

	public static InitYaml get() {
		return thisInc;
	}

	private Map<String, Object> map = null;
	private String hostName = null;
	private String root = null;
	private static final String filename = "properties";
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
		System.out.println("this.root: +" + this.root);
	}

	private String convert$(String s) {
		if (this.root != null) {
			s = s.replace("[ROOT]", this.root);
		}
		return s;
	}

	public String getValue(String key) {
		String s = (String) ((Map<String, Object>)map.get("SQLITE_JDBC")).get("URL");
		s = convert$(s);
		return s;
	}

}