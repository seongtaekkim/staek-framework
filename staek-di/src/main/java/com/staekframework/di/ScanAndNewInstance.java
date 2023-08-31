package com.staekframework.di;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/**
 * @author staek
 *
 * <p>
 *     인자로 넘어온 클래스의 인스턴스를 생성한다.
 *     인자로 넘어온 클래스의 @Inject인 필드의 인스턴스를 생성해 주입한다.
 *
 * </p>
 */
public class ScanAndNewInstance {

    public static <T> T getObject(Class<T> type) {
        T instance;
        instance = getInstance(type);
        Arrays.stream(type.getDeclaredFields()).forEach(f -> {
            if (f.getAnnotation(Inject.class) != null) {
                Object o = getInstance(f.getType());
                f.setAccessible(true);
                try {
                    f.set(instance, o);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return instance;
    }


    /**
     *
     * @param type
     * @return
     * @param <T>
     * @apiNote  예외는 모두 unchecked 하였다.
     */
    private static <T> T getInstance(Class<T> type) {
        try {
            return type.getConstructor(null).newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
