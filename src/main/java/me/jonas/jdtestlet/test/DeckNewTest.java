package me.jonas.jdtestlet.test;

import me.jonas.jdtestlet.core.JonasTestlet;
import me.jonas.jdtestlet.core.ResultConstant;
import me.jonas.jdtestlet.core.TestResult;
import me.jonas.jdtestlet.util.ResponseFactory;
import me.jonas.jdtestlet.util.ResponseFactoryImpl;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class DeckNewTest extends JonasTestlet {

    private String url = "https://deckofcardsapi.com/api/deck/new/";
    private ResponseFactory factory = new ResponseFactoryImpl(url);

    // Test Logic
    public TestResult testGet1() {
        TestResult result = null;
        HttpResponse response = null;
        try {
            response = factory.buildGetResponse();
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