package filter;

import controller.UserController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Dispatcher implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init dispatcher");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        System.out.println(request.getServletContext());
        System.out.println(request.getRequestURI());
        System.out.println(request.getRequestURL());

        String endPoint = request.getRequestURI().replaceAll(request.getContextPath(), "");
        System.out.println("앤드포잍느 : " + endPoint);

        UserController userController = new UserController();
        Method[] method = userController.getClass().getDeclaredMethods();
        for (Method m : method) {
            if (m.getName().equals("/" + endPoint)) {
                try {
                    m.invoke(userController);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void destroy() {
        System.out.println("destroy dispatcher");
    }
}
