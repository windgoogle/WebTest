package test;

import java.io.IOException;

import java.io.Writer;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThreadDeadlockDetector extends HttpServlet   {
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		com.codahale.metrics.jvm.ThreadDeadlockDetector detector=new com.codahale.metrics.jvm.ThreadDeadlockDetector();
		Set<String> deadThreadInfo=detector.getDeadlockedThreads();
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "must-revalidate,no-cache,no-store");
		
		Writer out= response.getWriter();
		if(deadThreadInfo==null||deadThreadInfo.isEmpty())
			out.write("no dead lock found!");
		for(String s:deadThreadInfo ) {
			out.write(s);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


}
