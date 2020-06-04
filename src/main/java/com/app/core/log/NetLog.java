package com.app.core.log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by phuong on 06/03/2017.
 */

public final class NetLog {
    private String type;
    private String status;
    private String url;
    private String timeReturn;
    private long startTime;
    private String result;
    private String error;
    private Map header;
    private JSONObject params;

    public NetLog() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTimeReturn() {
        return timeReturn;
    }

    public void setTimeReturn(String timeReturn) {
        this.timeReturn = timeReturn;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setResultString(String result) {
        this.result = result;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getHeader() {
        if (header != null) {
            return header.toString();
        }
        return "";
    }

    public void setHeader(Map header) {
        this.header = header;
    }

    public String getParams() {
        if (params != null) {
            try {
                return params.toString(2);
            } catch (JSONException e) {
                return params.toString();
            }
        }
        return "";
    }

    public void setParams(JSONObject params) {
        this.params = params;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
}
