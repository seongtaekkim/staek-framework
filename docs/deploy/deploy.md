



### project jar build

~~~sh
Manifest.txt 생성 # Main-Class: com.staekframework.test.Main # add
$ jar -cmf ../Manifest.txt ../staek-di.jar classes/com/* # classes 내부로 이동 후 진행해야 함
$ jar -xvf result.jar # jar 압축풀기
~~~





### staek-jdbc

- jar로 아카이빙 할 때 properties.yaml 을 제외한다.

~~~sh
$ jar -cf staek-jdbc.jar com/*
~~~

