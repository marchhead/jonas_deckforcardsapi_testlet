package me.jonas.jdtestlet.test;

import me.jonas.jdtestlet.core.JonasTestlet;
import me.jonas.jdtestlet.core.ResultConstant;
import me.jonas.jdtestlet.core.TestResult;
import me.jonas.jdtestlet.util.ResponseFactory;
import me.jonas.jdtestlet.util.ResponseFactoryImpl;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class DeckDrawTest extends JonasTestlet {
    private String url1 = "https://deckofcardsapi.com/api/deck/new/";
    private ResponseFactory factory1 = new ResponseFactoryImpl(url1);
    private String url2 = "https://deckofcardsapi.com/api/deck/<<deck_id>>/draw/";


    private String getId() throws NoSuchAlgorithmException, IOException, KeyManagementException {
        String deckId = "-1";
        HttpResponse response = null;

        response = factory1.buildGetResponse();
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        String responseContent = "";
        String line = "";
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream is = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    responseContent += line;
                }
            }
            deckId = responseContent.substring(responseContent.length() - 14, responseContent.length() - 2);
        }
        return deckId;

    }

    public TestResult testGet() {
        TestResult result = null;
        HttpResponse response = null;
        String deckId = "-1";
        try {
            deckId = getId();
        } catch (NoSuchAlgorithmException e) {
            result = new TestResult(ResultConstant.EXCEPTION, e.getMessage());
        } catch (IOException e) {
            result = new TestResult(ResultConstant.EXCEPTION, e.getMessage());
        } catch (KeyManagementException e) {
            result = new TestResult(ResultConstant.EXCEPTION, e.getMessage());
        }

        try {
            url2 = url2.replaceAll("<<deck_id>>", deckId);
            ResponseFactory factory2 = new ResponseFactoryImpl(url2);
            response = factory2.buildGetResponse();
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                result = new TestResult(ResultConstant.SUCCESS, response.getEntity().getContentType().toString());
            } else {
                result = new TestResult(ResultConstant.FAIL, statusLine.toString());
            }
        } catch (IOException e) {
            result = new TestResult(ResultConstant.EXCEPTION, e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            result = new TestResult(ResultConstant.EXCEPTION, e.getMessage());
        } catch (KeyManagementException e) {
            result = new TestResult(ResultConstant.EXCEPTION, e.getMessage());
        } finally {
            return result;
        }
    }
}
