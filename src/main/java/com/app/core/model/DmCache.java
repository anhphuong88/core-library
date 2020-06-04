package com.app.core.model;

public class DmCache<T> implements DmBase {
    private T result;
    private long time;
    private String term;

    public DmCache(T json, long time) {
        this.result = json;
        this.time = time;
    }

    public DmCache(T json, String term, long time) {
        this.result = json;
        this.time = time;
        this.term = term;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
