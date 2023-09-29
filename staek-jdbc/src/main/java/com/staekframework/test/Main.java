package com.staekframework.test;

import com.staekframework.test.User.*;


import java.util.Arrays;
import java.util.List;

public class Main {

    /**
     *
     * getOne(args ...)
     * getAll()
     * getList(args ...)
     * getCount()
     *
     */
    public static void main(String[] args) {

        /**
         * 전략패턴 구현으로,
         * dao factory 에서 다시 UserDao 객체만 생성해도 모든 dml 을 호출할 수 있게 되었다.
         */
//        UserDao userDao = new DaoFactory().newUserDao();
//       userDao.createTable();
//        userDao.deleteAll();
//
//        User user = new User("1","kim","1111", "10000");
//        userDao.insert(user);
//
//        user = new User("2","spring","2222", "15000");
//        userDao.insert(user);
//
//
//        user = new User("3","spring","2222", "20000");
//        userDao.insert(user);
//
//        System.out.println("count: " + userDao.count());
//
//        System.out.println("getALL==============================");
//        List<User> list = userDao.selectAll();
//        Arrays.stream(list.toArray()).forEach(System.out::println);
//
//        System.out.println("getOne =============================");
//        User user2 = userDao.selectOne("1","1111");
//        if (user2 != null)
//            System.out.println(user2.toString());
//
//        System.out.println("getList ===========================");
//        List<User> spring = userDao.selectList("spring");
//        Arrays.stream(spring.toArray()).forEach(System.out::println);
//
//        System.out.println("delete==========================");
//        userDao.delete("1");
//        System.out.println(userDao.selectOne("1","1111"));
    }
}
