package test;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestDBPool extends HttpServlet {
    /**
     * Default constructor.
     */
    private String dsJndiName="";
    private DataSource ds=null;
    private int sleepTime=1;
    private int delay=0;
    private int connThreads=1;
    private int keepaliveThreads=1;
   // private volatile boolean isKeepAlvieExist=false;
    private volatile boolean stopKeepAlive=false;
    private volatile List<Thread> keepAliveList=new ArrayList<Thread>(0);
    public TestDBPool() {
        // TODO Auto-generated constructor stub
    }


    public void init(ServletConfig config) throws ServletException {
        // TODO Auto-generated method stub

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out=response.getWriter();
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "must-revalidate,no-cache,no-store");
        dsJndiName=request.getParameter("ds");
        getDS();
        out.println("测试数据源连接池"+dsJndiName+" ,"+ds);

        String keepAliveThreadsStr=request.getParameter("keepaliveThreads");
        if(keepAliveThreadsStr!=null) {
            keepaliveThreads = Integer.parseInt(keepAliveThreadsStr);
        }

        String keepaliveStr=request.getParameter("stopKeepalive");
        stopKeepAlive=Boolean.valueOf(keepaliveStr);
        /*
        if(stopKeepAlive) {
            System.out.println("关闭KeepAlive连接。");
            out.println("关闭KeepAlive连接。");
            keepaliveThreads=0;
            waitKeepAliveThreadsDead();
        }

         */
        String sleepStr=request.getParameter("sleep");
        if(sleepStr!=null) {
            sleepTime = Integer.parseInt(sleepStr);
        }
        String connThreadsStr=request.getParameter("connThreads");
        if(connThreadsStr!=null) {
            connThreads = Integer.parseInt(connThreadsStr);
        }


        String delayStr= request.getParameter("delay");
        if(delayStr!=null) {
            delay = Integer.parseInt(delayStr);
        }
        out.println("线程数："+connThreads+" ,获取连接后， sleep "+sleepTime+" 秒。 延迟 delay  : "+delay+" 毫秒进行下一次连接。");
        startConnThreads();
       // if(!stopKeepAlive) {
            startkeepAliveThread();
        //}

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request,response);
    }


    public DataSource getDS(){
        try {
            InitialContext ctx=new InitialContext();
            ds=(DataSource) ctx.lookup(dsJndiName);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return ds;
    }


    private void delay(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startConnThreads(){
        for(int i=0;i<connThreads;i++){
            if(delay>0) {
                delay(delay);
            }
            Thread connThread=new Thread(new connThread());
            connThread.setName(dsJndiName+ "-ConnThread-"+i);
            connThread.start();

        }
    }

    public void startkeepAliveThread(){
      if(keepaliveThreads>=1) {
          for(int i=0;i<keepaliveThreads;i++) {
              Thread keepalive = new Thread(new KeepAliveThread());
              keepalive.setName(dsJndiName + "_keepalive_"+i);

            //  if(!keepAliveList.contains(keepalive)){
               //   keepAliveList.add(keepalive);
             // }
              keepalive.start();
          }
      }
    }

    public void waitKeepAliveThreadsDead(){
        while(keepAliveList.size()>0){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    class connThread implements  Runnable {

        @Override
        public void run() {
            Connection connection=null;
            Statement stmt=null;

            try {
                 connection=ds.getConnection();
                  System.out.println("Thread "+Thread.currentThread().getName()+" get connection :"+connection);
                  stmt=connection.createStatement();
                 stmt.executeQuery("select * from account0");
               delay(sleepTime);
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if(stmt!=null){
                    try {
                        stmt.close();
                    }catch (Exception e){

                    }
                }
                if(connection!=null){
                    try {
                        connection.close();
                    }catch (Exception e){

                    }
                }
            }
        }
    }

    class KeepAliveThread implements  Runnable {


        @Override
        public void run() {
            Connection connection = null;
            Statement stmt = null;
            long lasttime=System.currentTimeMillis();
            while (!stopKeepAlive) {
                try {
                    connection = ds.getConnection();
                    long now=System.currentTimeMillis();
                    if(now-lasttime>=120*1000) {
                        System.out.println("Thread " + Thread.currentThread().getName() + " get connection :" + connection);
                        lasttime=now;
                    }
                    stmt = connection.createStatement();
                    stmt.executeQuery("select * from account0");
                    delay(5000);
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Exception e) {

                        }
                    }
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (Exception e) {

                        }
                    }
                }
            }
            keepAliveList.remove(Thread.currentThread());
        }
    }


}
