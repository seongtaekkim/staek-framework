package com.staekframework.jdbc.yaml;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


public class LoadYaml {

	private static final String YAML_URL = "YAML_URL";
	public static Map<String, Object> getMap(String yamlName) {

		ClassLoader cl = LoadYaml.class.getClassLoader();
		yamlName = yamlName.replaceAll("\\.", "\\/");
		yamlName = yamlName + ".yaml";


		try (InputStream inputStream = cl.getResourceAsStream(yamlName)) {

			Yaml yaml = new Yaml();
			Map<String, Object> map = yaml.load(inputStream);
			String path = cl.getResource(yamlName).getPath();
			map.put(YAML_URL, path);
			return map;
		} catch (IOException e) {
            throw new RuntimeException(e);
        }

	}
}
