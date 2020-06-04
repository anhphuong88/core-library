package com.app.core.action;

public interface Action3<T, T1, T2> {
    void invoke(T out, T1 out1, T2 out2);
}

