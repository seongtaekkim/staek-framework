package com.staekframework.di;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ScanAndNewInstance {
    public static <T> T getObject(Class<T> type) {
        T instance;
        try {
            instance = type.getConstructor(null).newInstance();
            Arrays.stream(type.getDeclaredFields()).forEach(f -> {
                if (f.getAnnotation(Inject.class) != null) {
                    try {
                        Object o = f.getType().getConstructor(null).newInstance();
                        f.setAccessible(true);
                        f.set(instance, o);
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
            });
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return instance;

    }
}
