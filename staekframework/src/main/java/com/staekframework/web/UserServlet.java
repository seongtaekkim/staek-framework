package com.staekframework.web;

import com.staekframework.test.User.DaoFactory;
import com.staekframework.test.User.User;
import com.staekframework.test.User.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 클라이언트 값을 꺼내기 전에 문자셋 설정
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");

        UserDao userDao = new DaoFactory().newUserDao();
        userDao.createTable();
        userDao.deleteAll();

        User user = new User("1","kim","1111");
        userDao.insert(user);
        user = new User("2","spring","2222");
        userDao.insert(user);
        user = new User("3","spring","2222");
        userDao.insert(user);

        System.out.println("count: " + userDao.count());

        System.out.println("getALL==============================");
        List<User> list = userDao.selectAll();
        Arrays.stream(list.toArray()).forEach(System.out::println);

        System.out.println("getOne =============================");
        User user2 = userDao.selectOne("1","1111");
        if (user2 != null)
            System.out.println(user2.toString());

        System.out.println("getList ===========================");
        List<User> spring = userDao.selectList("spring");
        Arrays.stream(spring.toArray()).forEach(System.out::println);

        System.out.println("delete==========================");
        userDao.delete("1");
        System.out.println(userDao.selectOne("1","1111"));

        // 클라이언트로 출력하기 위해 준비한다.
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>유저목록</h1>");
        for (User vo : list) {
            out.println(vo.getName() + "님을 환영합니다.\n");
        }

        out.println("</body></html>");
    }
}
