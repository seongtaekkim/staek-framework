package me.staek.test;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/test/1")
public class ApiServerTest extends HttpServlet {

    public ApiServerTest() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println(req.getContentType());

        if ("application/json".equals(req.getContentType())) {
            BufferedReader br = req.getReader();
            String read;
            StringBuffer sb = new StringBuffer();
            while ((read=br.readLine()) != null)
                sb.append(read);
            System.out.println(sb);
        } else {
            //application/x-www-form-urlencoded
            String food = req.getParameter("food");
            String method = req.getParameter("method");
            System.out.println(food);
            System.out.println(method);
        }

//        resp.setContentType("text/plain; charset=utf-8");
        resp.setContentType("text/html; charset=utf-8");
//        resp.setContentType("application/json; charset=utf-8");
//        resp.sendRedirect("index.jsp");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("<h1>bye~</h1>");
        out.println("</body>");
        out.println("</html>");
//        out.println("{\"food\": "+ food + "}");
        out.flush();
    }
}
