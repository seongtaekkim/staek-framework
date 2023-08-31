

Manifest.txt 생성 # Main-Class: com.staekframework.test.Main
jar -cmf ../Manifest.txt ../staek-di.jar classes/com/* # classes 내부로 이동 후 진행해야 함
jar -xvf result.jar # jar 압축풀기


staek-di.jar 파일을 staekframework 의 lib 폴더로 move 한다.
