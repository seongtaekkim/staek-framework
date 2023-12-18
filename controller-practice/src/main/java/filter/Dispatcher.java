package filter;

import annotation.RequestMapping;
import controller.UserController;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Enumeration;

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

        boolean isFound = false;
//        System.out.println("request.getParameter  " + request.getParameter("username"));
        for (Method m : method) {
            RequestMapping annotation = m.getDeclaredAnnotation(RequestMapping.class);

            if (annotation.value().equals(endPoint)) {
                try {

                    Parameter[] param = m.getParameters();
                    String path = null;
                    if (param.length != 0) {
                            Object dto = param[0].getType().getConstructor().newInstance();
                            setDate(dto, request);
                            path = (String) m.invoke(userController, dto);
                        } else {
                            path = (String) m.invoke(userController);
                        }
                    RequestDispatcher rd = request.getRequestDispatcher(path);
                    rd.forward(request, response);
                } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
                isFound = true;
                break ;
            }
        }

         if (!isFound) {
            response.setContentType("text/html; charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.println("잘못된 주소");
            pw.flush();
        }
    }

    private <T> void setDate(T dto, HttpServletRequest request) throws InvocationTargetException, IllegalAccessException {
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String param = params.nextElement();
            String methodName = paramToMethodName(param);
            System.out.println(methodName);
            Method[] methods = dto.getClass().getDeclaredMethods();
            for (Method m : methods) {
                if (m.getName().equals(methodName)) {
                    /**
                     * 타입 체크 로직 필요함
                     */
                    m.invoke(dto, request.getParameter(param));
                }
            }
        }

    }

    private String paramToMethodName(String param) {
        String upper  = param.substring(0,1).toUpperCase();
        return "set" + upper + param.substring(1);
    }

    @Override
    public void destroy() {
        System.out.println("destroy dispatcher");
    }
}
