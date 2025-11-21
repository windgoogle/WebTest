package utils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class CipherSuiteViewer {
    public static void main(String[] args) {
        try {
            //Security.setProperty("crypto.policy", "unlimited");
            SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
            sslContext.init(null, null, null);
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            String[] cipherSuites = socketFactory.getSupportedCipherSuites();
            for (String cipherSuite : cipherSuites) {
                System.out.println(cipherSuite);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}