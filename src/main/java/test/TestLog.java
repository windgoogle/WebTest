package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestLog extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TestLog() {
        // TODO Auto-generated constructor stub
    }


	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//Logger logger = Logger.getLogger("javax.xml.messaging.saaj.soap", "com.sun.xml.internal.messaging.saaj.soap.LocalStrings");
		Log log= LogFactory.getLog(TestLog.class);
		/*
		logger.log(Level.INFO, "#########Begin###########");
		logger.log(Level.SEVERE, "#######SERVR##Begin###########");
		logger.log(Level.WARNING, "#######WARNING##Begin###########");
		logger.log(Level.FINEST, "#######FINEST##Begin###########");
		Logger logger2 = Logger.getLogger("javax.xml.messaging.saaj.soap.ver1_1", "com.sun.xml.internal.messaging.saaj.soap.ver1_1.LocalStrings");
		logger2.log(Level.INFO, "#########END###########");
		logger2.log(Level.SEVERE, "#######SERVER ##END###########");
		logger2.log(Level.WARNING, "#######WARNING ##END###########");
		logger2.log(Level.FINEST, "#######FINEST ##END###########");
		
		logger2.log(Level.FINER, "#######FINER ##END###########");

		 */
		log.info("sdfsdfsdfasdfsdfasdf");
		//System.getSecurityManager().checkExit(2);
		System.out.print("########################  1 , print print print !");
		System.out.print("######################## 2,  print print print !");
		System.out.print("######################## 3,  print print print !");
		System.out.println("######################## test done !");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("<a href='index.jsp' >return </a> ");
		/*

		Object result=null;
		try {
			InitialContext icontext = new InitialContext();
			result = icontext.lookup("java:comp/env/eis/XUConnectionFactory");
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {

		}

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out=response.getWriter();
		out.println("===result "+result);
		out.println("<a href='index.jsp' >return </a> ");
*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
