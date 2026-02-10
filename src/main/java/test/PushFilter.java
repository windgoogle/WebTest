package test;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.PushBuilder;
import java.io.IOException;

//@WebFilter(urlPatterns="/*")
public class PushFilter implements Filter {
    private PushBuilder pushBuilder;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("----------PushFilter  init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest)request;
        //String uri=httpServletRequest.getRequestURI();
        String uri=httpServletRequest.getServletPath();
        System.out.println("----- enter PushFilter doFilter-----"+uri);

        switch (uri) {
            case "/push.jsp":
                PushBuilder  pushBuilder=httpServletRequest.newPushBuilder();
                System.out.println("----- get pushbuilder -----"+pushBuilder);
               // pushBuilder=httpServletRequest.getPushBuilder();
                pushBuilder.path("/styles.css").push();
                pushBuilder.path("/logo.png").push();
                break;

            default:
                break;
        }
        chain.doFilter(request, response);;
        System.out.println("----- exit PushFilter -----"+uri);
    }

    @Override
    public void destroy() {

    }

}