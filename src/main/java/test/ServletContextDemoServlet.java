package test;


import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * ServletContext 新方法演示：addJspFile()、会话超时、请求编码
 */
@WebServlet("/contextDemo")
public class ServletContextDemoServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        // 获取ServletContext（Servlet上下文，全局唯一）
        ServletContext context = getServletContext();

        // ========== 1. 使用setRequestCharacterEncoding()设置全局默认请求编码 ==========
        // 作用：设置整个Web应用的默认请求字符编码（解决中文参数乱码）
        // 注意：若单个请求手动设置了编码，会覆盖此全局配置
      //  context.setRequestCharacterEncoding("UTF-8");
     //   System.out.println("全局默认请求编码已设置为：" + context.getRequestCharacterEncoding());

        // ========== 2. 使用setSessionTimeout()设置会话超时时间 ==========
        // 单位：秒（默认通常是30分钟=1800秒）
        // 作用：设置整个Web应用的会话超时时间，全局生效
       // context.setSessionTimeout(600); // 设置为10分钟（600秒）
       // System.out.println("会话超时时间已设置为：" + context.getSessionTimeout() + " 秒");

        // ========== 3. 使用addJspFile()动态注册JSP为Servlet ==========
        // 对比web.xml静态配置，此方法是运行时动态注册，无需修改web.xml
        // 参数1：Servlet名称；参数2：JSP文件路径（相对于WEB-INF）
        String servletName = "dynamicJspServlet";
        String jspFilePath = "/WEB-INF/dynamic.jsp";

        // 先判断是否已注册，避免重复注册报错
        if (context.getServletRegistration(servletName) == null) {
            // 动态注册JSP Servlet
            ServletRegistration.Dynamic dynamicServlet = context.addJspFile(servletName, jspFilePath);
            // 为动态注册的Servlet配置访问路径（映射URL）
            dynamicServlet.addMapping("/dynamicJsp");
            System.out.println("Dynamic register JSP Servlet successfully，path：/dynamicJsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        // 获取ServletContext的配置信息，展示给前端
        ServletContext context = getServletContext();
        out.println("<h1>ServletContext 方法演示结果</h1>");
        out.println("<p>1. 全局默认请求编码：" + context.getRequestCharacterEncoding() + "</p>");
        out.println("<p>2. 会话超时时间：" + context.getSessionTimeout() + " 秒（" + context.getSessionTimeout()/60 + " 分钟）</p>");
        out.println("<p>3. 动态注册的JSP访问链接：<a href='/ServletContextDemo/dynamicJsp?name=张三'>/dynamicJsp（带中文参数）</a></p>");

        out.close();
    }
}