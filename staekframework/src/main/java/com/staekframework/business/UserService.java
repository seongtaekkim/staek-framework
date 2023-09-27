package com.staekframework.business;

import com.staekframework.di.WireInject;
import com.staekframework.web.DataSourceTxManager;
import com.staekframework.web.DefaultTxDefinition;
import com.staekframework.web.TxManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserService {

    /**
     * 클래스가 이미 인스턴스가 생성된 경우여야만하는데
     * 그 생각을 못함.. 나중에 개선점으로 남겨두자.
     */
    @WireInject
    private UserDao userDao;

    private TxManager transactionManager;

    public void setTransactionManager(TxManager transactionManager) {
        this.transactionManager = transactionManager;
    }


    private UserDao getUserDao(HttpServletRequest req) {
        userDao = (UserDao) req.getServletContext().getAttribute("UserDao");
//        transactionManager = new DataSourceTransactionManager(userDa); // 임시
        transactionManager = new DataSourceTxManager(userDao.datasource); // 임시
        return userDao;
    }
    private UserDao getDataByUserDao(HttpServletRequest req) {

        if (userDao == null) {
            userDao = (UserDao) req.getServletContext().getAttribute("UserDao");
            userDao.createTable();
            userDao.deleteAll();
            User user = new User("1", "김성택", "1111", "10000");
            userDao.insert(user);
            user = new User("2", "staek", "2222", "15000");
            userDao.insert(user);
            user = new User("3", "seongtki", "2222", "20000");
            userDao.insert(user);
        }
        return userDao;
    }

    public String callList(HttpServletRequest req) {

//        String findStr = req.getParameter("findStr");
        List<User> users = getDataByUserDao(req).selectAll();
        req.setAttribute("users",users);

        /**
         * 어떤걸 리턴해주는게 좋은걸까
         */
        return "/jsp/list.jsp";
//        return "/WEB-INF/classes/com/staekframework/web/jsp/list.jsp";
    }

    private boolean checkPrice(User user) {
        int price = Integer.parseInt(user.getPrice());
        if (price > 10000) {
            return true;
        } else
            throw new IllegalArgumentException("Insufficient balance: " + price);
    }

    /**
     * 인출로직
     * user를 순회하면서 10000원 미만이면 예외처리 로직을 탄다.
     * 그런데, 이미 변경된 user는 변경된채로 적용이 되었다.
     * 만약, 중간에 로직이 실패했을 때 나머지 user가 적용되지 말아야 된다면 롤백이 필요할 것이다.
     *
     */
    public String callwithdrawal(HttpServletRequest req) throws Exception {

        TransactionStatus status = null;
        try {
            status = this.transactionManager
                    .getTransaction(new DefaultTxDefinition());
        } catch (TransactionException e) {
            throw new RuntimeException(e);
        }

        List<User> users = getDataByUserDao(req).selectAll();
        for (User user : users) {
            try {
                if (checkPrice(user)) {
                    User uptVo = new User(user.getId(), user.getName(), user.getPassword()
                            , Integer.toString(Integer.parseInt(user.getPrice()) - 10000));
                    userDao.update(uptVo);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                this.transactionManager.rollback(status);
                throw e;
            } finally {
                this.transactionManager.commit(status);
            }
        }

        users = getDataByUserDao(req).selectAll();
        req.setAttribute("users",users);
        return "/jsp/list.jsp";
    }
}
