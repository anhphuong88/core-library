package com.app.core.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.app.core.action.Action1;
import com.app.core.util.JsonUtil;
import com.app.core.util.MapUtil;
import com.app.core.util.NetUtil;
import com.app.core.util.StringUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
@Deprecated
public class NetConnection {
    public static OkHttpClient createRetryClient() {
        return new OkHttpClient.Builder().addInterceptor((chain -> {
            Request request = chain.request();
            Response response = chain.proceed(request);
            int count = 0;
            while ((!response.isSuccessful()) && count < 10) {
                count++;
                response = chain.proceed(request);
            }
            return response;
        })).build();
    }

    private OkHttpClient client;

    public static OkHttpClient createDefaultClient(@NonNull Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpClient createClient(long timeout, @NonNull Interceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .build();
    }

    public static NetConnection instance(@NonNull OkHttpClient okHttpClient) {
        NetConnection net = new NetConnection();
        net.client = okHttpClient;
        return net;
    }

    @Nullable
    public Call get(@NonNull String url, @NonNull Action1<NetResponse<String>> callBack, @Nullable Map<String, String> params, @Nullable Headers headers) {
        String link = url;
        if (!StringUtil.emptyTrim(link)) {
            if (params != null) {
                HttpUrl httpUrl = HttpUrl.parse(link);
                if (httpUrl != null) {
                    HttpUrl.Builder builder = httpUrl.newBuilder();
                    MapUtil.readKind(params, builder::addQueryParameter);
                    link = builder.build().toString();
                }
            }
            Request.Builder builder = new Request.Builder();
            if (headers != null) builder = builder.headers(headers);
            Request request = builder.url(link).build();
            Call c = client.newCall(request);
            try {
                Response response = c.execute();
                NetResponse<String> netResponse = new NetResponseImpl<>();
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        netResponse.code(response.code());
                        netResponse.message("");
                        String strBody;
                        try {
                            strBody = body.string();
                            netResponse.result(strBody);
                        } catch (IOException e) {
                            netResponse.result("");
                        }
                    }
                } else {
                    ResponseBody body = response.body();
                    if (body != null) {
                        String strBody;
                        try {
                            strBody = body.string();
                            netResponse.result(strBody);
                        } catch (IOException e) {
                            netResponse.result("");
                        }
                    }
                    if (response.code() == 500) netResponse.result("");
                    netResponse.code(response.code());
                    netResponse.message(response.message());
                    Response in = response.networkResponse();
                    if (in != null) {
                        netResponse.code(in.code());
                        netResponse.message(in.message() != null ? in.message() : "");
                    }
                }
                callBack.invoke(netResponse);
            } catch (SocketTimeoutException ex) {
                c.cancel();
                if (NetUtil.INSTANCE.isAvailable()) {
                    callBack.invoke(new NetResponseImpl<String>().result(null).code(1002).message(ex.getMessage() != null ? ex.getMessage() : ""));
                } else {
                    NetResponse<String> response = new NetResponseImpl<>();
                    response.code(1001);
                    response.message("");
                    callBack.invoke(response);
                }
            } catch (IOException e) {
                c.cancel();
                if (NetUtil.INSTANCE.isAvailable()) {
                    callBack.invoke(new NetResponseImpl<String>().result(null).code(0).message(e.getMessage() != null ? e.getMessage() : ""));
                } else {
                    NetResponse<String> response = new NetResponseImpl<>();
                    response.code(1001);
                    response.message("");
                    callBack.invoke(response);
                }
            }
            return c;
        }
        return null;
    }

    @Nullable
    public Call post(@NonNull String url, @NonNull Action1<NetResponse<String>> callBack, @NonNull Map<String, String> params, @Nullable Headers headers) {
        if (!StringUtil.emptyTrim(url)) {
            FormBody.Builder builder = new FormBody.Builder();
            MapUtil.readKind(params, builder::add);
            RequestBody formBody = builder.build();
            Request.Builder requestBuilder = new Request.Builder();
            if (headers != null) requestBuilder = requestBuilder.headers(headers);
            Request request = requestBuilder.url(url).post(formBody).build();
            Call c = client.newCall(request);
            try {
                Response response = c.execute();
                NetResponse<String> netResponse = new NetResponseImpl<>();
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        netResponse.code(response.code());
                        netResponse.message("");
                        String strBody;
                        try {
                            strBody = body.string();
                            netResponse.result(strBody);
                        } catch (IOException e) {
                            netResponse.result("");
                        }
                    }
                } else {
                    ResponseBody body = response.body();
                    if (body != null) {
                        String strBody;
                        try {
                            strBody = body.string();
                            netResponse.result(strBody);
                        } catch (IOException e) {
                            netResponse.result("");
                        }
                    }
                    if (response.code() == 500) netResponse.result("");
                    netResponse.code(response.code());
                    netResponse.message(response.message());
                    Response in = response.networkResponse();
                    if (in != null) {
                        netResponse.code(in.code());
                        netResponse.message(in.message() != null ? in.message() : "");
                    }
                }
                callBack.invoke(netResponse);
            } catch (SocketTimeoutException ex) {
                c.cancel();
                if (NetUtil.INSTANCE.isAvailable()) {
                    callBack.invoke(new NetResponseImpl<String>().result(null).code(1002).message(ex.getMessage() != null ? ex.getMessage() : ""));
                } else {
                    NetResponse<String> response = new NetResponseImpl<>();
                    response.code(1001);
                    response.message("");
                    callBack.invoke(response);
                }
            } catch (IOException e) {
                c.cancel();
                if (NetUtil.INSTANCE.isAvailable()) {
                    callBack.invoke(new NetResponseImpl<String>().result(null).code(0).message(e.getMessage() != null ? e.getMessage() : ""));
                } else {
                    NetResponse<String> response = new NetResponseImpl<>();
                    response.code(1001);
                    response.message("");
                    callBack.invoke(response);
                }
            }
            return c;
        }
        return null;
    }

    @Nullable
    public Call post(@NonNull String url, @NonNull Action1<NetResponse<String>> callBack, @NonNull String json, @Nullable Headers headers) {
        if (!StringUtil.emptyTrim(url) && JsonUtil.INSTANCE.isValid(json)) {
            RequestBody formBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            Request.Builder requestBuilder = new Request.Builder();
            if (headers != null) requestBuilder = requestBuilder.headers(headers);
            Request request = requestBuilder.url(url).post(formBody).build();
            Call c = client.newCall(request);
            try {
                Response response = c.execute();
                NetResponse<String> netResponse = new NetResponseImpl<>();
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        netResponse.code(response.code());
                        netResponse.message("");
                        String strBody;
                        try {
                            strBody = body.string();
                            netResponse.result(strBody);
                        } catch (IOException e) {
                            netResponse.result("");
                        }
                    }
                } else {
                    ResponseBody body = response.body();
                    if (body != null) {
                        String strBody;
                        try {
                            strBody = body.string();
                            netResponse.result(strBody);
                        } catch (IOException e) {
                            netResponse.result("");
                        }
                    }
                    if (response.code() == 500) netResponse.result("");
                    netResponse.code(response.code());
                    netResponse.message(response.message());
                    Response in = response.networkResponse();
                    if (in != null) {
                        netResponse.code(in.code());
                        netResponse.message(in.message() != null ? in.message() : "");
                    }
                }
                callBack.invoke(netResponse);
            } catch (SocketTimeoutException ex) {
                c.cancel();
                if (NetUtil.INSTANCE.isAvailable()) {
                    callBack.invoke(new NetResponseImpl<String>().result(null).code(1002).message(ex.getMessage() != null ? ex.getMessage() : ""));
                } else {
                    NetResponse<String> response = new NetResponseImpl<>();
                    response.code(1001);
                    response.message("");
                    callBack.invoke(response);
                }
            } catch (IOException e) {
                c.cancel();
                if (NetUtil.INSTANCE.isAvailable()) {
                    callBack.invoke(new NetResponseImpl<String>().result(null).code(0).message(e.getMessage() != null ? e.getMessage() : ""));
                } else {
                    NetResponse<String> response = new NetResponseImpl<>();
                    response.code(1001);
                    response.message("");
                    callBack.invoke(response);
                }
            }
            return c;
        }
        return null;
    }

    @Nullable
    public Call delete(@NonNull String url, @NonNull Action1<NetResponse<String>> callBack, @NonNull Map<String, String> params, @Nullable Headers headers) {
        if (!StringUtil.emptyTrim(url)) {
            FormBody.Builder builder = new FormBody.Builder();
            MapUtil.readKind(params, builder::add);
            RequestBody formBody = builder.build();
            Request.Builder requestBuilder = new Request.Builder();
            if (headers != null) requestBuilder = requestBuilder.headers(headers);
            Request request = requestBuilder.url(url).delete(formBody).build();
            Call c = client.newCall(request);
            try {
                Response response = c.execute();
                NetResponse<String> netResponse = new NetResponseImpl<>();
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        netResponse.code(response.code());
                        netResponse.message("");
                        String strBody;
                        try {
                            strBody = body.string();
                            netResponse.result(strBody);
                        } catch (IOException e) {
                            netResponse.result("");
                        }
                    }
                } else {
                    ResponseBody body = response.body();
                    if (body != null) {
                        String strBody;
                        try {
                            strBody = body.string();
                            netResponse.result(strBody);
                        } catch (IOException e) {
                            netResponse.result("");
                        }
                    }
                    if (response.code() == 500) netResponse.result("");
                    netResponse.code(response.code());
                    netResponse.message(response.message());
                    Response in = response.networkResponse();
                    if (in != null) {
                        netResponse.code(in.code());
                        netResponse.message(in.message() != null ? in.message() : "");
                    }
                }
                callBack.invoke(netResponse);
            } catch (SocketTimeoutException ex) {
                c.cancel();
                if (NetUtil.INSTANCE.isAvailable()) {
                    callBack.invoke(new NetResponseImpl<String>().result(null).code(1002).message(ex.getMessage() != null ? ex.getMessage() : ""));
                } else {
                    NetResponse<String> response = new NetResponseImpl<>();
                    response.code(1001);
                    response.message("");
                    callBack.invoke(response);
                }
            } catch (IOException e) {
                c.cancel();
                if (NetUtil.INSTANCE.isAvailable()) {
                    callBack.invoke(new NetResponseImpl<String>().result(null).code(0).message(e.getMessage() != null ? e.getMessage() : ""));
                } else {
                    NetResponse<String> response = new NetResponseImpl<>();
                    response.code(1001);
                    response.message("");
                    callBack.invoke(response);
                }
            }
            return c;
        }
        return null;
    }


    @Nullable
    public Call delete(@NonNull String url, @NonNull Action1<NetResponse<String>> callBack, @NonNull String json, @Nullable Headers headers) {
        if (!StringUtil.emptyTrim(url) && JsonUtil.INSTANCE.isValid(json)) {
            RequestBody formBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            Request.Builder requestBuilder = new Request.Builder();
            if (headers != null) requestBuilder = requestBuilder.headers(headers);
            Request request = requestBuilder.url(url).delete(formBody).build();
            Call c = client.newCall(request);
            try {
                Response response = c.execute();
                NetResponse<String> netResponse = new NetResponseImpl<>();
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        netResponse.code(response.code());
                        netResponse.message("");
                        String strBody;
                        try {
                            strBody = body.string();
                            netResponse.result(strBody);
                        } catch (IOException e) {
                            netResponse.result("");
                        }
                    }
                } else {
                    ResponseBody body = response.body();
                    if (body != null) {
                        String strBody;
                        try {
                            strBody = body.string();
                            netResponse.result(strBody);
                        } catch (IOException e) {
                            netResponse.result("");
                        }
                    }
                    if (response.code() == 500) netResponse.result("");
                    netResponse.code(response.code());
                    netResponse.message(response.message());
                    Response in = response.networkResponse();
                    if (in != null) {
                        netResponse.code(in.code());
                        netResponse.message(in.message() != null ? in.message() : "");
                    }
                }
                callBack.invoke(netResponse);
            } catch (SocketTimeoutException ex) {
                c.cancel();
                if (NetUtil.INSTANCE.isAvailable()) {
                    callBack.invoke(new NetResponseImpl<String>().result(null).code(1002).message(ex.getMessage() != null ? ex.getMessage() : ""));
                } else {
                    NetResponse<String> response = new NetResponseImpl<>();
                    response.code(1001);
                    response.message("");
                    callBack.invoke(response);
                }
            } catch (IOException e) {
                c.cancel();
                if (NetUtil.INSTANCE.isAvailable()) {
                    callBack.invoke(new NetResponseImpl<String>().result(null).code(0).message(e.getMessage() != null ? e.getMessage() : ""));
                } else {
                    NetResponse<String> response = new NetResponseImpl<>();
                    response.code(1001);
                    response.message("");
                    callBack.invoke(response);
                }
            }
            return c;
        }
        return null;
    }

    @Nullable
    public Call getAsync(@NonNull String url, @NonNull Action1<NetResponse<String>> callBack, @Nullable Map<String, String> params, @Nullable Headers headers) {
        String link = url;
        if (!StringUtil.emptyTrim(link)) {
            if (params != null) {
                HttpUrl httpUrl = HttpUrl.parse(link);
                if (httpUrl != null) {
                    HttpUrl.Builder builder = httpUrl.newBuilder();
                    MapUtil.readKind(params, builder::addQueryParameter);
                    link = builder.build().toString();
                }

            }
            Request.Builder builder = new Request.Builder();
            if (headers != null) builder = builder.headers(headers);
            Request request = builder.url(link).build();
            Call c = client.newCall(request);
            c.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    call.cancel();
                    if (NetUtil.INSTANCE.isAvailable()) {
                        callBack.invoke(new NetResponseImpl<String>().result(null).code(0).message(e.getMessage() != null ? e.getMessage() : ""));
                    } else {
                        NetResponse<String> response = new NetResponseImpl<>();
                        response.code(1001);
                        response.message("");
                        callBack.invoke(response);
                    }
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    NetResponse<String> netResponse = new NetResponseImpl<>();
                    if (response.isSuccessful()) {
                        ResponseBody body = response.body();
                        if (body != null) {
                            netResponse.code(response.code());
                            netResponse.message("");
                            String strBody;
                            try {
                                strBody = body.string();
                                netResponse.result(strBody);
                            } catch (IOException e) {
                                netResponse.result("");
                            }
                        }
                    } else {
                        ResponseBody body = response.body();
                        if (body != null) {
                            String strBody;
                            try {
                                strBody = body.string();
                                netResponse.result(strBody);
                            } catch (IOException e) {
                                netResponse.result("");
                            }
                        }
                        if (response.code() == 500) netResponse.result("");
                        netResponse.code(response.code());
                        netResponse.message(response.message());
                        Response in = response.networkResponse();
                        if (in != null) {
                            netResponse.code(in.code());
                            netResponse.message(in.message() != null ? in.message() : "");
                        }
                    }
                    callBack.invoke(netResponse);
                }
            });
            return c;
        }
        return null;
    }

    @Nullable
    public Call postAsync(@NonNull String url, @NonNull Action1<NetResponse<String>> callBack, @NonNull Map<String, String> params, @Nullable Headers headers) {
        if (!StringUtil.emptyTrim(url)) {
            FormBody.Builder builder = new FormBody.Builder();
            MapUtil.readKind(params, builder::add);
            RequestBody formBody = builder.build();
            Request.Builder requestBuilder = new Request.Builder();
            if (headers != null) requestBuilder = requestBuilder.headers(headers);
            Request request = requestBuilder.url(url).post(formBody).build();
            Call c = client.newCall(request);
            c.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    call.cancel();
                    if (NetUtil.INSTANCE.isAvailable()) {
                        callBack.invoke(new NetResponseImpl<String>().result(null).code(0).message(e.getMessage() != null ? e.getMessage() : ""));
                    } else {
                        NetResponse<String> response = new NetResponseImpl<>();
                        response.code(1001);
                        response.message("");
                        callBack.invoke(response);
                    }
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    NetResponse<String> netResponse = new NetResponseImpl<>();
                    if (response.isSuccessful()) {
                        ResponseBody body = response.body();
                        if (body != null) {
                            netResponse.code(response.code());
                            netResponse.message("");
                            String strBody;
                            try {
                                strBody = body.string();
                                netResponse.result(strBody);
                            } catch (IOException e) {
                                netResponse.result("");
                            }
                        }
                    } else {
                        ResponseBody body = response.body();
                        if (body != null) {
                            String strBody;
                            try {
                                strBody = body.string();
                                netResponse.result(strBody);
                            } catch (IOException e) {
                                netResponse.result("");
                            }
                        }
                        if (response.code() == 500) netResponse.result("");
                        netResponse.code(response.code());
                        netResponse.message(response.message());
                        Response in = response.networkResponse();
                        if (in != null) {
                            netResponse.code(in.code());
                            netResponse.message(in.message() != null ? in.message() : "");
                        }
                    }
                    callBack.invoke(netResponse);
                }
            });
            return c;
        }
        return null;
    }

    @Nullable
    public Call postAsync(@NonNull String url, @NonNull Action1<NetResponse<String>> callBack, @NonNull String json, @Nullable Headers headers) {
        if (!StringUtil.emptyTrim(url)) {
            RequestBody formBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            Request.Builder requestBuilder = new Request.Builder();
            if (headers != null) requestBuilder = requestBuilder.headers(headers);
            Request request = requestBuilder.url(url).post(formBody).build();
            Call c = client.newCall(request);
            c.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    call.cancel();
                    if (NetUtil.INSTANCE.isAvailable()) {
                        callBack.invoke(new NetResponseImpl<String>().result(null).code(0).message(e.getMessage() != null ? e.getMessage() : ""));
                    } else {
                        NetResponse<String> response = new NetResponseImpl<>();
                        response.code(1001);
                        response.message("");
                        callBack.invoke(response);
                    }
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    NetResponse<String> netResponse = new NetResponseImpl<>();
                    if (response.isSuccessful()) {
                        ResponseBody body = response.body();
                        if (body != null) {
                            netResponse.code(response.code());
                            netResponse.message("");
                            String strBody;
                            try {
                                strBody = body.string();
                                netResponse.result(strBody);
                            } catch (IOException e) {
                                netResponse.result("");
                            }
                        }
                    } else {
                        ResponseBody body = response.body();
                        if (body != null) {
                            String strBody;
                            try {
                                strBody = body.string();
                                netResponse.result(strBody);
                            } catch (IOException e) {
                                netResponse.result("");
                            }
                        }
                        if (response.code() == 500) netResponse.result("");
                        netResponse.code(response.code());
                        netResponse.message(response.message());
                        Response in = response.networkResponse();
                        if (in != null) {
                            netResponse.code(in.code());
                            netResponse.message(in.message() != null ? in.message() : "");
                        }
                    }
                    callBack.invoke(netResponse);
                }
            });
            return c;
        }
        return null;
    }

    @Nullable
    public Call deleteAsync(@NonNull String url, @NonNull Action1<NetResponse<String>> callBack, @Nullable Headers headers) {
        if (!StringUtil.emptyTrim(url)) {
            Request.Builder requestBuilder = new Request.Builder();
            if (headers != null) requestBuilder = requestBuilder.headers(headers);
            Request request = requestBuilder.url(url).delete().build();
            Call c = client.newCall(request);
            c.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    call.cancel();
                    if (NetUtil.INSTANCE.isAvailable()) {
                        callBack.invoke(new NetResponseImpl<String>().result(null).code(0).message(e.getMessage() != null ? e.getMessage() : ""));
                    } else {
                        NetResponse<String> response = new NetResponseImpl<>();
                        response.code(1001);
                        response.message("");
                        callBack.invoke(response);
                    }
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    NetResponse<String> netResponse = new NetResponseImpl<>();
                    if (response.isSuccessful()) {
                        ResponseBody body = response.body();
                        if (body != null) {
                            netResponse.code(response.code());
                            netResponse.message("");
                            String strBody;
                            try {
                                strBody = body.string();
                                netResponse.result(strBody);
                            } catch (IOException e) {
                                netResponse.result("");
                            }
                        }
                    } else {
                        ResponseBody body = response.body();
                        if (body != null) {
                            String strBody;
                            try {
                                strBody = body.string();
                                netResponse.result(strBody);
                            } catch (IOException e) {
                                netResponse.result("");
                            }
                        }
                        if (response.code() == 500) netResponse.result("");
                        netResponse.code(response.code());
                        netResponse.message(response.message());
                        Response in = response.networkResponse();
                        if (in != null) {
                            netResponse.code(in.code());
                            netResponse.message(in.message() != null ? in.message() : "");
                        }
                    }
                    callBack.invoke(netResponse);
                }
            });
            return c;
        }
        return null;
    }

    @Nullable
    public Call deleteAsync(@NonNull String url, @NonNull Action1<NetResponse<String>> callBack, @NonNull Map<String, String> params, @Nullable Headers headers) {
        if (!StringUtil.emptyTrim(url)) {
            FormBody.Builder builder = new FormBody.Builder();
            MapUtil.readKind(params, builder::add);
            RequestBody formBody = builder.build();
            Request.Builder requestBuilder = new Request.Builder();
            if (headers != null) requestBuilder = requestBuilder.headers(headers);
            Request request = requestBuilder.url(url).delete(formBody).build();
            Call c = client.newCall(request);
            c.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    call.cancel();
                    if (NetUtil.INSTANCE.isAvailable()) {
                        callBack.invoke(new NetResponseImpl<String>().result(null).code(0).message(e.getMessage() != null ? e.getMessage() : ""));
                    } else {
                        NetResponse<String> response = new NetResponseImpl<>();
                        response.code(1001);
                        response.message("");
                        callBack.invoke(response);
                    }
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    NetResponse<String> netResponse = new NetResponseImpl<>();
                    if (response.isSuccessful()) {
                        ResponseBody body = response.body();
                        if (body != null) {
                            netResponse.code(response.code());
                            netResponse.message("");
                            String strBody;
                            try {
                                strBody = body.string();
                                netResponse.result(strBody);
                            } catch (IOException e) {
                                netResponse.result("");
                            }
                        }
                    } else {
                        ResponseBody body = response.body();
                        if (body != null) {
                            String strBody;
                            try {
                                strBody = body.string();
                                netResponse.result(strBody);
                            } catch (IOException e) {
                                netResponse.result("");
                            }
                        }
                        if (response.code() == 500) netResponse.result("");
                        netResponse.code(response.code());
                        netResponse.message(response.message());
                        Response in = response.networkResponse();
                        if (in != null) {
                            netResponse.code(in.code());
                            netResponse.message(in.message() != null ? in.message() : "");
                        }
                    }
                    callBack.invoke(netResponse);
                }
            });
            return c;
        }
        return null;
    }

    @Nullable
    public Call deleteAsync(@NonNull String url, @NonNull Action1<NetResponse<String>> callBack, @NonNull String json, @Nullable Headers headers) {
        if (!StringUtil.emptyTrim(url)) {
            RequestBody formBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            Request.Builder requestBuilder = new Request.Builder();
            if (headers != null) requestBuilder = requestBuilder.headers(headers);
            Request request = requestBuilder.url(url).delete(formBody).build();
            Call c = client.newCall(request);
            c.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    call.cancel();
                    if (NetUtil.INSTANCE.isAvailable()) {
                        callBack.invoke(new NetResponseImpl<String>().result(null).code(0).message(e.getMessage() != null ? e.getMessage() : ""));
                    } else {
                        NetResponse<String> response = new NetResponseImpl<>();
                        response.code(1001);
                        response.message("");
                        callBack.invoke(response);
                    }
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) {
                    NetResponse<String> netResponse = new NetResponseImpl<>();
                    if (response.isSuccessful()) {
                        ResponseBody body = response.body();
                        if (body != null) {
                            netResponse.code(response.code());
                            netResponse.message("");
                            String strBody;
                            try {
                                strBody = body.string();
                                netResponse.result(strBody);
                            } catch (IOException e) {
                                netResponse.result("");
                            }
                        }
                    } else {
                        ResponseBody body = response.body();
                        if (body != null) {
                            String strBody;
                            try {
                                strBody = body.string();
                                netResponse.result(strBody);
                            } catch (IOException e) {
                                netResponse.result("");
                            }
                        }
                        if (response.code() == 500) netResponse.result("");
                        netResponse.code(response.code());
                        netResponse.message(response.message());
                        Response in = response.networkResponse();
                        if (in != null) {
                            netResponse.code(in.code());
                            netResponse.message(in.message() != null ? in.message() : "");
                        }
                    }
                    callBack.invoke(netResponse);
                }
            });
            return c;
        }
        return null;
    }
}
