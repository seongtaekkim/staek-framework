package com.staekframework.di;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author staek
 *
 * <p>
 *     basePackage 내부에 모든 클래스를 조회한다.
 *     @Inject가 있는 클래스만 인스턴스를 생성한다.
 *     @Inject인 필드의 인스턴스를 생성해 주입한다.
 *
 * </p>
 */
public class ScanAndNewInstance {

    private final String basePackage = "com.staekframework";

    private final Map<String, Object> instanceMap = new ConcurrentHashMap<>();

    /**
     * basePakage를 정한다.
     * scan한 클래스 리스트에서 @Inject 를 찾아 인스턴스를 생성한다.
     */
    public void scanAndCreateInstance() {
        List<Class<?>> classes = scan(basePackage);
        for (Class<?> findClass : classes) {
            Inject annotation = findClass.getAnnotation(Inject.class);
            if (annotation != null) {
                Object object = getObject(findClass);
                instanceMap.put(findClass.getName(), object);
            }
        }

        for (Class<?> findClass : classes) {
            Repository annotation = findClass.getAnnotation(Repository.class);
            if (annotation != null) {
                Arrays.stream(findClass.getDeclaredConstructors()).forEach(new Consumer<Constructor<?>>() {
                    @Override
                    public void accept(Constructor<?> f) {
                        f.setAccessible(true);
                        boolean flag = false;
                        if (Arrays.stream(f.getParameterTypes()).count() == 1) {
                            Class<?> findInterface = null;
                            String findKey = null;
                            for (String string : instanceMap.keySet()) {
                                Object o = instanceMap.get(string);
                                for (int i = 0 ; i < Arrays.stream(o.getClass().getInterfaces()).count() ; i++) {
                                    findInterface = o.getClass().getInterfaces()[i];
                                    if (findInterface.getName().equals(f.getParameterTypes()[0].getName())) {
                                        flag = true;
                                        findKey = string;
                                        break ;
                                    }
                                }
                                if (flag == true) break;
                            }
                            if (flag == false) {
                                throw new NullPointerException("constructor parameter");
                            }
                            instanceMap.put(f.getName()
                                    , getInstance(findClass, instanceMap.get(findKey), findInterface));
                        } else {
                            throw new RuntimeException("at least one parametar");
                        }
                    }
                });
            }
        }
    }

    /**
     * basepakage 를 path 로 파싱한다.
     * ClassLoader 를 이용해서 모든 디렉토리를 검색하여 basepackage 와 함께 findClasses 를 호출하고
     * 결과를 리턴한다.
     *
     */
    public List<Class<?>> scan(String basePackageName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = basePackageName.replace('.', '/');

        List<Class<?>> classes = new ArrayList<Class<?>>();

        try {
            List<File> files = new ArrayList<File>();
            Enumeration<URL> resources = classLoader.getResources(path);
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                files.add(new File(resource.getFile()));
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    //System.out.println("[Directory] " + file.getAbsolutePath());
                    classes.addAll(findClasses(file, basePackageName));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }


    /**
     *
     * 입력받은 파일을 검색해서 class 파일을 찾아 리턴할 리스트에 담아준다.
     * 파일 검색중 디렉터리가 나오면 재귀로 다시 요청한다.
     *
     * @throws class 파일이 없을 시 예외처리 고민 필요.
     */
    private List<Class<?>> findClasses(File directory, String packageName) {
        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                //System.out.println("[Directory] " + file.getAbsolutePath());
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                //System.out.println("[File] " + file.getAbsolutePath());
                String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                try {
                    classes.add(Class.forName(className, false, classLoader));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return classes;
    }

    /**
     * 입력반은 클래스 인스턴스를 생성한다.
     * @Inject 가 있는 필드의 인스턴스를 생성해 주입한다.
     */
    public <T> T getObject(Class<T> type) {
        T instance = getInstance(type);
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
     * create instance
     * @apiNote  예외는 모두 unchecked 하였다.
     */
    private <T> T getInstance(Class<T> type) {
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

    /**
     * constructor with one param
     */
    private <T> T getInstance(Class<T> type, Object param, Class<?> parameterType) {
        try {
            return type.getConstructor(parameterType).newInstance(param);
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

    /**
     * 특정 인스턴스를 objectMap에 등록한다.
     */
    public void putInstance(Object o) {
        instanceMap.put(o.getClass().getName(), o);
    }


    public int size() {
        return this.instanceMap.size();
    }

    /**
     * registed instance
     */
    public Map<String, Object> getInstance(String name) {
        Object o = this.instanceMap.get(name);
        if (o == null) {
            throw new RuntimeException("not found instance :" + name);
        }
        return instanceMap;
    }

}
