package com.staekframework.di.classinfo;

import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * TODO class 정보 조회 방법
 *      1. Class.class, 2. new Class, 3. Class.forName
 */
public class ClssInfoView {
    public static void main(String[] args) throws ClassNotFoundException {

        /**
         * class 로딩시 힙에 클래스정보가 저장된다.
         */
        Class<FortyTwoStudent> bookClass = FortyTwoStudent.class;


        System.out.println("public fields ==========================");
        Arrays.stream(bookClass.getFields()).forEach(System.out::println); // only public field
        System.out.println("all fields ==========================");
        Arrays.stream(bookClass.getDeclaredFields()).forEach(System.out::println); // 접근제한자 상관 없이 모두 조회 가능함.


        FortyTwoStudent student= new FortyTwoStudent(7, "seontki");

        /**
         * 객체 생성된 인스턴스의 class 정보
         */
        Class<? extends FortyTwoStudent> aClass1 = student.getClass();

        System.out.println("필드정보와 필드 값 조회 ==========================");
        Arrays.stream(bookClass.getDeclaredFields()).forEach(f -> {
            f.setAccessible(true);
            try {
                System.out.printf("%s, %s\n", f, f.get(student));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("method 정보 조회================================");
        Arrays.stream(bookClass.getMethods()).forEach(System.out::println);
        System.out.println("생성자 정보 조회================================");
        Arrays.stream(bookClass.getDeclaredConstructors()).forEach(System.out::println);
        System.out.println("부모클래스 정보 조회================================");
        System.out.println(bookClass.getSuperclass());
        System.out.println("인터페이스 정보 조회================================");
        Arrays.stream(FortyTwoStudent.class.getInterfaces()).forEach(System.out::println);
        Arrays.stream(student.getClass().getInterfaces()).forEach(System.out::println);

        System.out.println("필드정보 및 필드의 메타정보 조회================================");
        Arrays.stream(FortyTwoStudent.class.getDeclaredFields()).forEach(f -> {
            int modifiers = f.getModifiers();
            System.out.println(f);
            System.out.println(Modifier.isPrivate(modifiers));
            System.out.println(Modifier.isStatic(modifiers));
        });
        System.out.println("함수정보 및 함수의 메타정보 조회 ================================");
        Arrays.stream(FortyTwoStudent.class.getMethods()).forEach(f -> {
            int modifiers = f.getModifiers();
            System.out.println(f + " " + Modifier.isPrivate(modifiers));
        });


        /**
         * Class.forName 으로 class 정보를 조회
         */
        Class<?> aClass = Class.forName("com.staekframework.di.classinfo.FortyTwoStudent");
    }
}
