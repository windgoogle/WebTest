package perf;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class DBTest
 */
@WebServlet("/perf/DBTest")
public class DBTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static DataSource ds;   
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DBTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "text/html;charset=UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select id,name,sex,age,height,addr,id_code,tel from db_test");
			out.append("<table>");
			while( rs.next() ) {
				out.append("<tr><td>").append(rs.getInt("id")+"").append("</td><td>")
				.append(rs.getString("name")).append("</td><td>")
				.append(rs.getInt("sex")+"").append("</td><td>")
				.append(rs.getInt("age")+"").append("</td><td>")
				.append(rs.getInt("height")+"").append("</td><td>")
				.append(rs.getString("addr")).append("</td><td>")
				.append(rs.getString("id_code")).append("</td><td>")
				.append(rs.getString("tel")).append("</td></tr>");
			}
			out.append("</table>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null ) stmt.close();
				if (rs != null ) rs.close();
				if (conn != null ) conn.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Context context = new InitialContext();
			this.ds = (DataSource)context.lookup("java:comp/env/jdbc/db_test");

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
		this.ds = null;
	}
}
