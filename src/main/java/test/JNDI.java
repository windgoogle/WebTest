package test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class JNDI extends HttpServlet {


    /**
     * @see HttpServlet#HttpServlet()
     */
    public JNDI() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "must-revalidate,no-cache,no-store");
        Writer out= response.getWriter();
        String localIp=java.net.InetAddress.getLocalHost().getHostAddress();
        System.out.println("-----local host : "+localIp);
        out.write("Node : "+localIp+" service request !");
        out.write("\n");
        //System.exit(0);

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }
}
