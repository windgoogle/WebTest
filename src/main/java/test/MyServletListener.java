package test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
       System.out.println("========listener Initialzed.");
        //long s=10*60*1000;
       // long s=2*60*1000;
        //long s=1*60/10*1000;
       // try {
         //   Thread.sleep(s);
      //  } catch (InterruptedException e) {
        //    e.printStackTrace();
      //  }
      //  System.out.println("========listener initialized.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("========listener destoryed.");
    }
}
