package test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.sql.*;
import javax.sql.*;
import javax.naming.InitialContext;
import javax.naming.Context;
import javax.script.ScriptEngineManager;

/**
 * Servlet implementation class TestServlet
 */
public class TestConnPool extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final List<Connection> cachedConnections = new ArrayList();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TestConnPool() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().append("Served at: ").append(request.getContextPath());
        testDB();

        System.out.println("------------");
    }

    public void testDB() {
        Context initialContext = null;
        DataSource dataSource = null;
        Connection con = null;
        PreparedStatement stmt = null;

        try {
            initialContext = new InitialContext();
            //Context  in2 =  (Context) initialContext.lookup("java:comp/env");
            dataSource = (DataSource) initialContext.lookup("testdb");

            con = dataSource.getConnection();

            System.out.println(con.getClass().getName() + "********" + con.hashCode());
            //????false
            System.out.println("con.getAutoCommit():"+con.getAutoCommit());
            // con.setAutoCommit(false);

            String queryStr1 = "select  1";
            stmt = con.prepareStatement(queryStr1);
            stmt.executeQuery(queryStr1).next();

            System.out.println("=========end===");
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }



}