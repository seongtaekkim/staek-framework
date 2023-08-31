package com.staekframework.di.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection 을 통해 클래스의 생성자를 생성하려고 한다.
 * Constructor, Method, Field class를 통해 class 리소스에 접근해서 데이터를 수정,생성 해보자.
 */
public class ReflectionPractice {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {


        /**
         * TODO class 정보로부터 constructor 를 가져와 인스턴스를 생성한다.
         */
        Class<?> aClass = Class.forName("com.staekframework.di.reflection.SubClass");
        Constructor<?> constructor = aClass.getConstructor(String.class);
        Object o = constructor.newInstance("constructor1");
        System.out.println(o);

        Constructor<SubClass> constructor1 = SubClass.class.getConstructor(String.class);
        SubClass constructor2 = constructor1.newInstance("constructor2");
        System.out.println(constructor2);


        /**
         * TODO 모든 필드에 접근할 수 있고, 데이터를 get,set 할 수 있다.
         */
        Field field1 = SubClass.class.getDeclaredField("field1");
        System.out.println(field1.get(null));

        Field field2 = SubClass.class.getDeclaredField("field2");
        field2.setAccessible(true); // private 접근가능하도록 코드
        System.out.println(field2.get(o));
        field2.set(o, "modified field2");
        System.out.println(field2.get(o));


        /**
         * TODO 모든 메서드에 접근할 수 있고, 실행할 수 있다.
         */
        Method method1 = SubClass.class.getDeclaredMethod("method1");
        method1.invoke(o);

        Method concat = SubClass.class.getDeclaredMethod("concat", String.class, String.class);
        Object invoke = concat.invoke(o, "string1", "string2");
        System.out.println((String)invoke);

    }
}
