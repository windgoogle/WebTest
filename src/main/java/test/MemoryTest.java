package test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

/**
 * ÿ�������Ĳ���m���Ǳ�ʾ��Ӧ����������M�ڴ�
 *
 */
public class MemoryTest extends HttpServlet  {
    private static final int _1m = 1024*1024;

    private static final long THREAD_SLEEP_MS = 10*1000;

    public static void main(String[] args) throws Exception{
        youngAllocate(1000);
        oldAllocate(1000);
        metaspaceAllocate(200000);
        directMemoryAllocate(400);
        // threadStackAllocate(400);
        Thread.sleep(60000);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "must-revalidate,no-cache,no-store");
        response.setCharacterEncoding("UTF-8");
      

        PrintWriter out = response.getWriter();
        try {
            out.println("young: " + 1000 + "m .........");
            youngAllocate(1000);
            out.println("young end .");

            out.println("old: " + 1000 + "m ...........");
            oldAllocate(1000);
            out.println("old end .");

            out.println("metaspace: " + 200000 + "m .........");
            metaspaceAllocate(200000);
            out.println("metaspace end .");


            out.println("metaspace: " + 400 + "m .........");
            directMemoryAllocate(400);
            out.println("metaspace end .");

            out.println("sleep: " + 60 + "s .........");
            Thread.sleep(60000);
            out.println("Thread sleep end. ");

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * @param count �ظ������MyCalc��������
     */
    private static void metaspaceAllocate(int count) throws Exception {
        System.out.println("metaspace object count: " + count);

        Method declaredMethod = ClassLoader.class.getDeclaredMethod("defineClass",
                new Class[]{String.class, byte[].class, int.class, int.class});
        declaredMethod.setAccessible(true);

        File classFile = new File("/app/afei/MyCalc.class");
        byte[] bcs = new byte[(int) classFile.length()];
        try(InputStream is = new FileInputStream(classFile);){
            // ���ļ�������byte����
            while (is.read(bcs)!=-1){
            }
        }

        int outputCount = count/10;
        for (int i=1; i<=count; i++){
            try {
                // �ظ�����MyCalc�����
                declaredMethod.invoke(
                        MemoryTest.class.getClassLoader(),
                        new Object[]{"MyCalc", bcs, 0, bcs.length});
            }catch (Throwable e){
                // �ظ���������׳�LinkageError: attempted  duplicate class definition for name: "MyCalc"
                // System.err.println(e.getCause().getLocalizedMessage());
            }
            if (i>=outputCount && i%outputCount==0){
                System.out.println("i = "+i);
            }
        }
        System.out.println("metaspace end");
    }

    /**
     * @param m �������M direct memory
     */
    private static void directMemoryAllocate(int m){
        System.out.println("direct memory: "+m+"m");
        for (int i = 0; i < m; i++) {
            ByteBuffer.allocateDirect(_1m);
        }
        System.out.println("direct memory end");
    }

    /**
     * @param m ��young���������M������
     */
    private static void youngAllocate(int m){
        System.out.println("young: "+m+"m");
        for (int i = 0; i < m; i++) {
            byte[] test = new byte[_1m];
        }
        System.out.println("young end");
    }

    /**
     * ��Ҫ���ò���: -XX:PretenureSizeThreshold=2M, ���ҽ��CMS
     * @param m ��old���������M������
     */
    private static void oldAllocate(int m){
        System.out.println("old:   "+m+"m");
        for (int i = 0; i < m/5; i++) {
            byte[] test = new byte[5*_1m];
        }
        System.out.println("old end");
    }

    // ��Ҫ���ò���: -Xss10240k, �����ʵ����ʧ�ܸ���
    private static void threadStackAllocate(int m){
        int threadCount = m/10;
        System.out.println("thread stack count:"+threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(() -> {
                System.out.println("thread name: " + Thread.currentThread().getName());
                try {
                    while(true) {
                        Thread.sleep(THREAD_SLEEP_MS);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println("thread stack end:"+threadCount);
    }
}