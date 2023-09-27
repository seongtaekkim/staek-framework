package com.staekframework.main.businiss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
