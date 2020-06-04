package com.app.core.network;


import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DefaultHeaderInterceptor implements Interceptor {
    private String name;
    private String value;
    public DefaultHeaderInterceptor(@NonNull String name, @NonNull String value) {
        super();
        this.name = name;
        this.value = value;
    }

    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request requestWithUserAgent = originalRequest
                .newBuilder()
                .header(name, value)
                .build();

        return chain.proceed(requestWithUserAgent);
    }
}
