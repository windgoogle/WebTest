package test;

import java.io.IOException;


import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.locks.LockSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestLockSupport extends HttpServlet  {
	

	private static final long serialVersionUID = 1L;
	private static List<Thread> threadsLocked=new Vector<Thread>();

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String op=request.getParameter("unpark");
		 if("true".equals(op)){
			 for(Thread t: threadsLocked){
				  LockSupport.unpark(t);//释放许可
				  System.out.println("  unpark ()  done!");
			 }
		 }else{
			 Thread thread = Thread.currentThread();
			 System.out.println("  park() start !");
			 response.setStatus(HttpServletResponse.SC_OK);
				response.setContentType("text/plain");
				response.setHeader("Cache-Control", "must-revalidate,no-cache,no-store");
				
				Writer out= response.getWriter();
				String localIp=java.net.InetAddress.getLocalHost().getHostAddress();
				System.out.println("-----local host : "+localIp);
				out.write("Node : "+localIp+" serviceing request !");
				out.write("\n");
				  
			 threadsLocked.add(thread);
	         LockSupport.park();// 获取许可
	         System.out.println("  park ()  done!");
		 }
	    
		 request.getRequestDispatcher("/index2.jsp").forward(request, response);
	    
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
