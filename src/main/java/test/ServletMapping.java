package test;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet({"/sm", "*.ext"})
public class ServletMapping extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        HttpServletMapping mapping = request.getHttpServletMapping();
        MappingMatch match = mapping.getMappingMatch();
        String value = mapping.getMatchValue();
        String pattern = mapping.getPattern();
        String servletName = mapping.getServletName();

        response.setContentType("text/html");
        response.getWriter().println("<html><body>");
        response.getWriter().println("<head>show HttpServletMapping api :</head>");
        response.getWriter().println("<li>getMappingMatch(): " + match+"</li>");
        response.getWriter().println("<li>getMatchValue(): " + value+"</li>");
        response.getWriter().println("<li>getPattern(): " + pattern+"</li>");
        response.getWriter().println("<li>getServletName(): " + servletName+"</li>");
        response.getWriter().println("</body></html>");
    }

}