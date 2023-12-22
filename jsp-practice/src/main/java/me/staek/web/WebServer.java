package me.staek.web;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.http.MimeHeaders;
import org.apache.tomcat.util.http.fileupload.util.mime.MimeUtility;

import java.io.File;

public class WebServer {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        File file = new File(".");
        System.out.println(file.getAbsoluteFile());
        tomcat.addWebapp("", file.getAbsolutePath() + "/web");
        tomcat.start();
        tomcat.getServer().await(); // 요청대기
    }
}
