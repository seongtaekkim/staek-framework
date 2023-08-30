package com.staekframework.di.classinfo;

import java.util.Arrays;

/**
 * Reflection을 이용해서 Annotation 사용된 클래스,필드등의 정보 및 값을 읽어들일 수 있다.
 */
public class AnnotationPractice {
    public static void main(String[] args) {

        /**
         * .getAnnotations() : 정의된 annotation을 조회한다. @Inheric 인 경우에 부모의 annotation도 가져온다 (중복 제외)
         * .getDeclaredAnnotations : 정의된 annotation을 조회한다.
         */
        Arrays.stream(Student.class.getAnnotations()).forEach(System.out::println);
        Arrays.stream(Student.class.getDeclaredAnnotations()).forEach(System.out::println);
        Arrays.stream(FortyTwoStudent.class.getAnnotations()).forEach(System.out::println); // 부모에 정의되어 있으므로 출력.
        Arrays.stream(FortyTwoStudent.class.getDeclaredAnnotations()).forEach(System.out::println); // 정의 안되어 있으므로 출력 안함.

        /**
         * Student 에 정의된 모든 필드 중에서 annotation 정보를 출력
         */
        Arrays.stream(Student.class.getDeclaredFields()).forEach(f -> {
            Arrays.stream(f.getAnnotations()).forEach(System.out::println);
        });


        /**
         * Student 에 정의된 모든 필드 중에서 annotation 정보를 가져오고,
         * 그 중 StudentTypeAnnotation 타입인 경우의 값을 출력.
         */
        Arrays.stream(Student.class.getDeclaredFields()).forEach(f -> {
            Arrays.stream(f.getAnnotations()).forEach(m -> {
                if (m instanceof StudentTypeAnnotation) {
                    StudentTypeAnnotation m1 = (StudentTypeAnnotation) m;
                    System.out.println(m1.type());
                }
            });
        });
    }
}
