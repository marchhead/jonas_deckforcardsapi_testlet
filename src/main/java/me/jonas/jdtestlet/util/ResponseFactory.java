package me.jonas.jdtestlet.util;

import org.apache.http.HttpResponse;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public interface ResponseFactory {
    HttpResponse buildGetResponse() throws IOException, NoSuchAlgorithmException, KeyManagementException;
    HttpResponse buildPostResponse(String body) throws IOException, NoSuchAlgorithmException, KeyManagementException;
    HttpResponse buildPutResponse(String body) throws IOException, NoSuchAlgorithmException, KeyManagementException;
    HttpResponse buildDeleteResponse() throws IOException, NoSuchAlgorithmException, KeyManagementException;
}
