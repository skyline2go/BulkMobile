package com.example.atest;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;
public class CustomSSLSocketFactoryTLS12 extends SSLSocketFactory {
    SSLContext sslContext = SSLContext.getInstance("TLSv1.2");

    public CustomSSLSocketFactoryTLS12(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
        super(truststore);

        TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sslContext.init(null, new TrustManager[] { tm }, null);
    }

    @Override
    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
    	SSLSocket s = (SSLSocket)sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
    	s.setEnabledProtocols(new String[] {"TLSv1.2"} );
        return s;
    }

    @Override
    public Socket createSocket() throws IOException {
    	SSLSocket s = (SSLSocket)sslContext.getSocketFactory().createSocket();
    	s.setEnabledProtocols(new String[] {"TLSv1.2"} );
        return s;
    }
}
