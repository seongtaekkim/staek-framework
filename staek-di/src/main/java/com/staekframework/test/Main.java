package com.staekframework.test;


import com.staekframework.di.ScanAndNewInstance;

/**
 * TODO 임시실행코드 (JUnit 테스트 코드로 이관 예정)
 */
public class Main {
    public static void main(String[] args) {
        /**
         * 단일 클래스 인스턴스 생성
         */
//        RepositoryClass object = ScanAndNewInstance.getObject(RepositoryClass.class);
//        System.out.println(object);

        /**
         * 단일 클래스 인스턴스 생성
         * - 필드 인스턴스 생성 (현재는 새로 생성하면서 주입하는 형태임)
         */
//        ServiceClass object1 = ScanAndNewInstance.getObject(ServiceClass.class);
//        System.out.println(object1);
//        System.out.println(object1.repositoryClass);


        /**
         * 특정 인스턴스 등록
         */
        Datasource ds = new ConcreteDataresource();
        ScanAndNewInstance scan = new ScanAndNewInstance();
        scan.putInstance(ds);
        /**
         * 모든 클래스를 조회한다.
         * @Inject 인 클래스만 인스턴스를 생성한다.
         * 클래스 내부에 @Inject 가 있는 필드에 인스턴스를 생성해서 주입한다.
         */
        scan.scanAndCreateInstance();
    }
}
