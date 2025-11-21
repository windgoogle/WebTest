package test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;

public class JCATest extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/plain");
        response.setHeader("Cache-Control", "must-revalidate,no-cache,no-store");
        Writer out= response.getWriter();
        String rest=lisetProtocol();
        System.out.println(rest);
        out.write(rest);
        out.write("\n");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request,response);
    }

    public static void main(String[] args) {
      System.out.println(lisetProtocol());
    }


    private static String  lisetProtocol() {
        StringBuffer sb = new StringBuffer() ;
        for (Provider provider : Security.getProviders()) {

            sb.append("Provider: " + provider.getName() + " (ver " + provider.getVersion() + ")");
            sb.append("\n");
            sb.append("  Algorithms: ");
            ArrayList<String> algos = new ArrayList<String>();
            for (Provider.Service service : provider.getServices())
            {
                algos.add(String.format( "%s (%s)", service.getAlgorithm(), service.getType()));
            }
            java.util.Collections.sort(algos);
            String algorsStr = algos.toString();
            // remove [ and ] from ArrayList's toString()
            algorsStr = algorsStr.substring(1, algorsStr.length()-1);
            sb.append(algorsStr);
            sb.append("\n");
            sb.append("\n");
        }

        return sb.toString();
    }


}