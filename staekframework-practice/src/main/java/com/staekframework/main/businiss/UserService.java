package com.staekframework.main.businiss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserDao userDao;

    private boolean checkPrice(User user) {
        int price = Integer.parseInt(user.getPrice());
        if (price > 10000) {
            return true;
        } else
            throw new IllegalArgumentException("Insufficient balance: " + price);
    }

    @Transactional
    public void callwithdrawal_declare() throws Exception {

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

    PlatformTransactionManager transactionManager;

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void callwithdrawal_program() throws Exception {
        TransactionStatus status = this.transactionManager
                .getTransaction(new DefaultTransactionDefinition());


        List<User> users = userDao.selectAll();
        for (User user : users) {
            try {
                if (checkPrice(user)) {
                    User uptVo = new User(user.getId(), user.getName(), user.getPassword()
                            , Integer.toString(Integer.parseInt(user.getPrice()) - 10000));
                    userDao.update(uptVo);
                }
            } catch (Exception e) {
                this.transactionManager.rollback(status);
                throw e;
            }
        }
        this.transactionManager.commit(status);
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
