package test;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Set;

public class TestThreadSleep  extends HttpServlet {
    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out=response.getWriter();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "must-revalidate,no-cache,no-store");

        System.out.println("current thread :"+Thread.currentThread().getName() +" sleep 2 min ...`");
        out.write("thread sleep 2 min...... ");
        out.println("    ");
        out.println("  </br>  ");
        out.println("<a href='index.jsp' >return </a> ");
        try {
            Thread.sleep(2*60*1000);
        } catch (InterruptedException e) {
            StringWriter sw=new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            out.println("  occure exception : ");
            out.println(sw.toString());
            e.printStackTrace();
        }
        out.println("    ");
        out.println("  </br>  ");
        System.out.println("current thread :"+Thread.currentThread().getName() +" sleep end ...`");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request,response);
    }


}
