package test;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 使用注解配置过滤器，拦截所有以 "/api/" 开头的请求
//@WebFilter(urlPatterns = "/perf/*")
public class MyHttpFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 在请求处理前执行的逻辑（例如：日志记录、权限校验）
        System.out.println("HttpFilter >>>>>Intercept HTTP request <<before request>>- " + request.getRequestURI());
        System.out.println("----- global  request encoding-----"+request.getCharacterEncoding());
        System.out.println("----- global response encoding -----"+response.getCharacterEncoding());
        // 继续执行过滤器链（调用下一个过滤器或目标资源）
        chain.doFilter(request, response);

        // 在响应返回后执行的逻辑（例如：修改响应头、记录响应时间）
        System.out.println("HttpFilter >>>>>Finished <<after response>> - " + response.getStatus());
    }
}
