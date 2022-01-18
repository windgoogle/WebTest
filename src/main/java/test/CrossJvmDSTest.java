package test;

import utils.WebLogicJndiUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CrossJvmDSTest extends HttpServlet {

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CrossJvmDSTest() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Oracle数据库连接对象
         //Connection oracleConn = null;
           //MySQL数据库连接对象
            Connection mysqlConn = null;
               Statement stmt = null;
              ResultSet rs = null;
               try {
                     // String sqlOracle = "SELECT * FROM LEAD_OAMS_APPLICATIONS";
                     //获取数据库连接对象
                   //  oracleConn = WebLogicJndiUtil.getOracleConnection();
                     //stmt = oracleConn.createStatement();
                    /*rs = stmt.executeQuery(sqlOracle);
                        while (rs.next()) {
                                System.out.println(rs.getString("RESOURCEID")+"---"+rs.getString("APP_NAME"));
                         }
                         */
                   //System.out.println("-----------------------------oracleConn :"+oracleConn);
                   System.out.println("-----------------------------分割线-------------------------------------");

                      //String sqlMySQL = "SELECT * FROM LEAD_OAMS_DBSOURCES";
                     //获取数据库连接对象
                   mysqlConn = WebLogicJndiUtil.getMySQLConnection();
                   System.out.println("-----------------------------mysqlConn :"+mysqlConn);
                   stmt = mysqlConn.createStatement();
                    /*  rs = stmt.executeQuery(sqlMySQL);
                  while (rs.next()) {
                              System.out.println(rs.getString("RESOURCEID")+"---"+rs.getString("DBSOURCE_NAME"));
                         }*/
                  } catch (SQLException e) {
                     e.printStackTrace();
                }finally{
                      try {
                            // rs.close();
                             stmt.close();
                             // oracleConn.close();
                               mysqlConn.close();
                          } catch (SQLException e) {
                              e.printStackTrace();
                           }
                     }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request,response);
    }
}
