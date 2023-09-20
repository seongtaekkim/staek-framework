# servlet



## 서버 데이터저장소 개선과정

### 1. Init Servlet

- 서버 구동시 작성된 class를 1회 실행한다.
- 이 때 ServletContext에 userdao 를 생성해 넣어주었다.

~~~xml
<servlet>
   <servlet-name>test</servlet-name>
   <servlet-class>com.staekframework.web.MasterController
   </servlet-class>
  <load-on-startup>1</load-on-startup>
</servlet>
~~~

~~~java
public class MasterController extends HttpServlet {
  ...
@Override
public void init(ServletConfig config) throws ServletException {
    super.init(config);
    this.getServletContext().setAttribute("userDao",new DaoFactory().newUserDao());

}

@Override
public void destroy() {
    super.destroy();
    this.getServletContext().	
}
  ...
}
~~~



### 2. ServletContextListener

- **Init Servlet** 사용 안하고 이거 사용함.
- 웹어플리케이션이 시작되거나 종료될때 서블릿컨테이너는 listener를 호출하는데, 이때 필요한 자원을 준비하는게 관리가 더 좋다,
- 서버구동시 실행된다. 
- DAO 등의 자원을 미리 생성해서 ServletContext 저장소에 넣었다.

~~~java
@WebListener
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        context.setAttribute("userDao",new DaoFactory().newUserDao());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //TODO
    }
}
~~~



