package com.staekframework.test.User;

import java.sql.SQLException;
import java.util.List;

public class UserService {


    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    private boolean checkPrice(User user) {
        int price = Integer.parseInt(user.getPrice());
        if (price > 10000) {
            return true;
        } else
            throw new IllegalArgumentException("Insufficient balance: " + price);
    }

    /**
     * 프로그래밍적 트랜젝션 적용
     */
    public void callwithdrawal_program() throws Exception {
        List<User> users = userDao.selectAll();
        try {
            userDao.datasource.getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (User user : users) {
            try {
                if (checkPrice(user)) {
                User uptVo = new User(user.getId(), user.getName(), user.getPassword()
                        , Integer.toString(Integer.parseInt(user.getPrice()) - 10000));

                userDao.update(uptVo);
                }
            } catch (Exception e) {
                userDao.datasource.getConnection().rollback();
                userDao.datasource.getConnection().setAutoCommit(true);
                throw e;
            }
        }
        userDao.datasource.getConnection().commit();
        userDao.datasource.getConnection().setAutoCommit(true);
    }

    public void callwithdrawal() throws Exception {

        List<User> users = userDao.selectAll();
        for (User user : users) {
            try {
                if (checkPrice(user)) {
                    User uptVo = new User(user.getId(), user.getName(), user.getPassword()
                            , Integer.toString(Integer.parseInt(user.getPrice()) - 10000));
                    userDao.update(uptVo);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw e;
            }
        }
    }
}
