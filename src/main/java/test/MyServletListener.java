package test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        //long s=10*60*1000;
       // long s=2*60*1000;
        //long s=1*60/10*1000;
       // try {
         //   Thread.sleep(s);
      //  } catch (InterruptedException e) {
        //    e.printStackTrace();
      //  }
      //  System.out.println("========listener initialized.");
        // 获取ServletContext
        ServletContext context = sce.getServletContext();

        // 1. 设置全局请求编码
        context.setRequestCharacterEncoding("UTF-8");
        // 2. 设置会话超时时间（15分钟=900秒）
        context.setSessionTimeout(900);
        // 3. 动态注册JSP（可选，和Servlet中选其一即可）
        if (context.getServletRegistration("dynamicJspByListener") == null) {
            context.addJspFile("dynamicJspByListener", "/WEB-INF/dynamic.jsp")
                    .addMapping("/dynamicJspByListener");
        }

        System.out.println("web application started,ServletContext Initialization complete.");
        System.out.println("default reqeust encoding " + context.getRequestCharacterEncoding());
        System.out.println("session timeout " + context.getSessionTimeout() + "s");
        System.out.println("========listener Initialzed.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("========listener destoryed.");
    }
}
