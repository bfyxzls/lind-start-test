package com.lind.http2.config;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.util.Arrays;

@Slf4j
public class HttpClientUtils {
    /**
     *  获取安全的加密（Https）的HttpClient
     * @return
     */
    public static OkHttpClient getTLSOKHttp() {
        InputStream trustStorePath = HttpClientUtils.class.getResourceAsStream("/tomcat.keystore");
        log.info("包含授信公钥文件的路径：{}", trustStorePath);
        KeyStore keyStore = getKeyStore("123456", trustStorePath);
        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
        //DefaultTrustManager trustManager = new DefaultTrustManager();

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, new TrustManager[] { trustManager }, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        //OkHttpClient okHttpClient = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, trustManager).build();
        // 强行不验证hostName
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().protocols(Arrays.asList(Protocol.HTTP_1_1, Protocol.HTTP_2))
                .hostnameVerifier((String s, SSLSession sslSession) -> true)
                //.sslSocketFactory(sslSocketFactory).build();
                .sslSocketFactory(sslSocketFactory, trustManager).build();
        return okHttpClient;
    }

    /**
     * 获得keyStore
     * @param password
     * @param keyStorePath
     * @return
     */
    public static KeyStore getKeyStore(String password, InputStream keyStorePath) {
        KeyStore ks = null;
        //FileInputStream is = null;
        try {
            // 实例化密钥库 KeyStore用于存放证书，创建对象时 指定交换数字证书的加密标准
            // 指定交换数字证书的加密标准
            ks = KeyStore.getInstance("JKS");
            // 获得密钥库文件流
            //is = new FileInputStream(keyStorePath);
            // 加载密钥库
            ks.load(keyStorePath, password.toCharArray());
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                // 关闭密钥库文件流
                keyStorePath.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ks;
    }

    /**
     *  获取安全的加密（Https）的HttpClient，信任
     * @return
     */
    public static OkHttpClient getAllSafeOKHttp() {
        DefaultTrustManager trustManager = new DefaultTrustManager();

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, new TrustManager[] { trustManager }, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        //OkHttpClient okHttpClient = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, trustManager).build();
        // 强行不验证hostName
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().protocols(Arrays.asList(Protocol.HTTP_1_1, Protocol.HTTP_2))
                .hostnameVerifier((String s, SSLSession sslSession) -> true)
                .sslSocketFactory(sslSocketFactory).build();
        //.sslSocketFactory(sslSocketFactory, trustManager).build();
        return okHttpClient;
    }

    private static class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

    }

}
