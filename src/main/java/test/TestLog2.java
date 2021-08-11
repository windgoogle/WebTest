package test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLog2  extends HttpServlet {

    private volatile  boolean stop=false;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        final PrintWriter out=response.getWriter();
        int n=300;
        CountDownLatch latch=new CountDownLatch(n);
        long t1=System.currentTimeMillis();
        long t2=0;
        for(int i=0;i<n;i++){
            new Thread(new Err(latch)).start();
        }
        try {
       /*
          new Thread() {
              @Override
              public void run() {
                  out.println("finding System.err , "+System.err.hashCode()+" </BR>");
                  out.println("finding System.out , "+System.out.hashCode()+" </BR>");
                  while(!stop) {
                      ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
                      long[] ids = threadMXBean.getAllThreadIds();
                      ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(ids, true, false);

                      for (ThreadInfo threadInfo : threadInfos) {
                          MonitorInfo[] monitorInfos=threadInfo.getLockedMonitors();
                          if(monitorInfos!=null) {
                              for (MonitorInfo monitorInfo :monitorInfos){
                                  //out.println(monitorInfo.getClassName()+"@"+monitorInfo.getIdentityHashCode()+"<BR>");
                                  if(monitorInfo.getClassName().equals(System.err.getClass().getName())){
                                      out.println("found <BR>");
                                      out.println("Thread ID : "+threadInfo.getThreadId()+"["+threadInfo.getThreadName()+"],"+monitorInfo.getClassName()+"@"+monitorInfo.getIdentityHashCode()+"<BR>");
                                      continue ;
                                  }
                              }
                          }
                          //out.println("......</BR>");
                      }


                      try {
                          Thread.sleep(30);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                          Thread.currentThread().interrupt();
                      }
                  }

              }
          }.start();
          */
          latch.await();
          stop=true;
        } catch (InterruptedException e) {
           out.println(e);
        }finally {
            t2=System.currentTimeMillis();
        }

        out.println("cost time : "+(t2-t1) +" ms");

        out.println("</br>");

        out.println("<a href='index.jsp' >return </a> ");

    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                 doGet(request,response);
    }


    class Err implements Runnable {

        CountDownLatch latch;

        public Err(CountDownLatch latch){
            this.latch=latch;
        }

        public void run() {
            //for(int i=0;i<50;i++) {
              //  System.err.println("this is a system err println");
            //}
          try {
              for (int i = 0; i < 30; i++) {
                  Thread.dumpStack();
              }
              for (int i = 0; i < 50; i++) {
                  System.out.println("this is a system out println");
              }
          }catch (Throwable e){
              e.printStackTrace();
          }finally {
              latch.countDown();
          }


        }
    }


}
