package com.app.core.parser;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public final class Parser {
    public static <T> T fromJson(String json, Class<T> classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }

    public static <T> T fromJson(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    public static <T> String toJson(T model) {
        Gson gson = new Gson();
        return gson.toJson(model);
    }

    public static <T> String toJson(T model, Type type) {
        Gson gson = new Gson();
        return gson.toJson(model, type);
    }
}
