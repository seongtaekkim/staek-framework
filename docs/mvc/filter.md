# filter



```java
/**
 * DD파일에 등록하지 않아도 urlpattern 정보 작성가능
 * 필터순서기능은 없고, @Ordered 를 만들거나 DD파일에 순서대로 작성하면 적용된다. (나중에)
 */
@WebFilter(
    urlPatterns="/*",
    initParams={
       @WebInitParam(name="encoding",value="UTF-8")
    })
public class CharacterEncodingFilter implements Filter {
    FilterConfig config;

    /**
     * app 실행시 실행됨
     */
    @Override
    public void init(FilterConfig config) throws ServletException {
       this.config = config;
    }
    
    @Override
    public void doFilter(
          ServletRequest request, ServletResponse response,
          FilterChain nextFilter) throws IOException, ServletException {
       /**
        * before servlet 작업
        */
       request.setCharacterEncoding(config.getInitParameter("encoding"));

       /**
        * 다음필터 호출, 없으면 서블릿 service() 호출
        */
       nextFilter.doFilter(request, response);

       /**
        * 서블릿 실행 후, 클라이언트에게 응답하기 전에 할 작업
        */
    }

    @Override
    public void destroy() {
       System.out.println("filter destroy");
    }
}
```







## ref

https://sasca37.tistory.com/290