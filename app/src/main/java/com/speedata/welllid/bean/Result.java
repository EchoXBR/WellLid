package com.speedata.welllid.bean;

import java.util.Map;

/**
 * 请求数据返回状态
 */
public class Result {

    /**
     * 接口是否调用成功
     */
    private boolean success;

    /**
     * 接口返回数据
     */
    private Map<String, Object> data;


    /**
     * 接口调用失败错误说明
     */
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
