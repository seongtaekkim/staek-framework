package com.staekframework.test.User;

import com.staekframework.tx.DefaultTxManager;
import com.staekframework.tx.TxManager;

import java.util.List;

public class UserService {


    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    private boolean checkPrice(User user, int checkPrice) {
        int price = Integer.parseInt(user.getPrice());
        if (price > checkPrice) {
            return true;
        } else
            throw new IllegalArgumentException("Insufficient balance: " + price);
    }


    /**
     * 프로그래밍적 트랜젝션 적용
     */
    public void createUser(User user) {

        TxManager tx = new DefaultTxManager(userDao.datasource.getConnection());
        try {
            tx.startTx();
            if (checkPrice(user, 1000)) {
                userDao.insert(user);
            }
        } catch (IllegalArgumentException e) {
            tx.rollback();
            throw e;
        }
        tx.commit();
    }

    /**
     * 프로그래밍적 트랜젝션 적용
     */
    public void callwithdrawal_program() throws Exception {
        List<User> users = userDao.selectAll();

        TxManager tx = new DefaultTxManager(userDao.datasource.getConnection());
        tx.startTx();
        for (User user : users) {
            try {
                if (checkPrice(user, 10000)) {
                User uptVo = new User(user.getId(), user.getName(), user.getPassword()
                        , Integer.toString(Integer.parseInt(user.getPrice()) - 10000));

                userDao.update(uptVo);
                }
            } catch (Exception e) {
                tx.rollback();
                throw e;
            }
        }
        tx.commit();
    }

    public void callwithdrawal() throws Exception {

        List<User> users = userDao.selectAll();
        for (User user : users) {
            try {
                if (checkPrice(user, 100000)) {
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
