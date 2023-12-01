package com.hcmus.socket;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.security.KeyStore;

public class SocketSession {
    private ClientSocketHandler handler;

    public SocketSession() {
        try {
            handler = new ClientSocketHandler(new URI("ws://localhost:8443/chat"));
            handler.connect();

            String STORETYPE = "PKCS12";
            String KEYSTORE = Paths.get("chatapphcmus.p12").toString();
            String STOREPASSWORD = "hcmusa18";
            String KEYPASSWORD = "hcmusa18";

            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());

            File kf = new File(KEYSTORE);
            ks.load(new FileInputStream(kf), STOREPASSWORD.toCharArray());

            ks.load(new FileInputStream(kf), STOREPASSWORD.toCharArray());

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, KEYPASSWORD.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            SSLContext sslContext = null;
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            // sslContext.init( null, null, null ); // will use java's default key and trust store which is sufficient unless you deal with self-signed certificates

            SSLSocketFactory factory = sslContext
                    .getSocketFactory();// (SSLSocketFactory) SSLSocketFactory.getDefault();

            handler.setSocketFactory(factory);

            handler.connectBlocking();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
