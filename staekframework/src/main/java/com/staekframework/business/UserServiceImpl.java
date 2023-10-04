package com.staekframework.business;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    private boolean checkPrice(User user, int checkPrice) {
        int price = Integer.parseInt(user.getPrice());
        if (price > checkPrice) {
            return true;
        } else
            throw new IllegalArgumentException("Insufficient balance: " + price);
    }

    @Override
    public List<User> users() {
        return this.userDao.selectAll();
    }


    /**
     * 프로그래밍적 트랜젝션 적용
     */
    @Override
    public void createUser(User user) {
        userDao.insert(user);
        if (checkPrice(user, 1000)) {
        }
    }

    /**
     * 프로그래밍적 트랜젝션 적용
     */
    @Override
    public void callwithdrawal_program() {

        List<User> users = userDao.selectAll();
        for (User user : users) {
            if (checkPrice(user, 10000)) {
            User uptVo = new User(user.getId(), user.getName(), user.getPassword()
                    , Integer.toString(Integer.parseInt(user.getPrice()) - 10000));
            userDao.update(uptVo);
            }
        }
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
