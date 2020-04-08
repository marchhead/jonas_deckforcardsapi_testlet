package me.jonas.jdtestlet.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class ResponseFactoryImpl implements ResponseFactory{

    private CloseableHttpClient httpclient;
    private String url;

    public ResponseFactoryImpl(String url) {
        this.url = url;
    }

    private void refresh() throws KeyManagementException, NoSuchAlgorithmException {
        //Creating SSLContextBuilder object
        SSLContextBuilder SSLBuilder = SSLContexts.custom();

        //Building the SSLContext usiong the build() method
        SSLContext sslcontext = null;

        sslcontext = SSLBuilder.build();

        //Creating SSLConnectionSocketFactory object
        SSLConnectionSocketFactory sslConSocFactory = new SSLConnectionSocketFactory(sslcontext, new NoopHostnameVerifier());

        //Creating HttpClientBuilder
        HttpClientBuilder clientbuilder = HttpClients.custom();

        //Setting the SSLConnectionSocketFactory
        clientbuilder = clientbuilder.setSSLSocketFactory(sslConSocFactory);

        //Building the CloseableHttpClient
        httpclient = clientbuilder.build();
    }

    public HttpResponse buildGetResponse() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        refresh();
        HttpGet httpget = new HttpGet(url);
        //Executing the request
        HttpResponse httpresponse = httpclient.execute(httpget);
        return httpresponse;
    }

    public HttpResponse buildPostResponse(String body) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        refresh();
        return null;
    }

    public HttpResponse buildPutResponse(String body) throws IOException, NoSuchAlgorithmException, KeyManagementException{
        refresh();
        return null;
    }

    public HttpResponse buildDeleteResponse() throws IOException, NoSuchAlgorithmException, KeyManagementException{
        refresh();
        return null;
    }
}
