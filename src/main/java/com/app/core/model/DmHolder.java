package com.app.core.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class DmHolder<T> implements DmBase {
    private List<T> lst;
    private String fn;
    private Object payLoad;
    private int index;

    public DmHolder(String fn) {
        this.fn = fn;
    }

    public DmHolder(@NonNull String fn, List<T> value) {
        this.lst = value;
        this.fn = fn;
    }

    public DmHolder(@NonNull String fn, int index, @Nullable Object payLoad) {
        this.fn = fn;
        this.payLoad = payLoad;
        this.index = index;
    }

    public Object getPayLoad() {
        return payLoad;
    }

    public int getIndex() {
        return index;
    }

    public List<T> getLst() {
        return lst;
    }

    public String getFn() {
        return fn;
    }
}
