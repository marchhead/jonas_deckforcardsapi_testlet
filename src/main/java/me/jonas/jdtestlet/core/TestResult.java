package me.jonas.jdtestlet.core;

public class TestResult {
    private int result;
    private String msg;

    public TestResult(int result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "result=" + result +
                ", msg='" + msg + '\'' +
                '}';
    }
}
