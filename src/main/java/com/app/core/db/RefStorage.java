package com.app.core.db;

import android.content.Context;
import android.support.annotation.NonNull;

import com.app.core.model.DmCache;

import java.lang.reflect.Type;
import java.util.List;

import javax.crypto.SecretKey;

public final class RefStorage {

    // Basic reference

    public static boolean delete(String key) {
        if (Preference.contains(key)) {
            Preference.delete(key);
            return true;
        }
        return false;
    }

    public static boolean contain(String key) {
        return Preference.contains(key);
    }

    public static void put(String key, Object object) {
        Preference.put(key, object);
    }

    public static <T> T get(String key, Class<T> a) {
        return Preference.get(key, a);
    }

    public static <T> T get(String key, Type type) {
        return Preference.get(key, type);
    }

    public static <T, E> E get(String key, Class<?> eClass, Class<T> a) {
        return Preference.get(key, eClass, a);
    }

    public static <E> E get(String key, Class<?> eClass, Type type) {
        return Preference.get(key, eClass, type);
    }

    public static <T> List<T> getList(String key, Class<T> a) {
        return Preference.getList(key, a);
    }

    public static <T> List<T> getList(String key, Type type) {
        return Preference.getList(key, type);
    }

    public static <E, T> E getList(String key, Class<?> eClass, Class<T> a) {
        return Preference.getList(key, eClass, a);
    }


    public static <E> E getList(String key, Class<?> eClass, Type type) {
        return Preference.getList(key, eClass, type);
    }

    public static void init(@NonNull Context context) {
        Preference.init(context);
    }

    public static void saveBool(Boolean value, String key) {
        Preference.put(key, value);
    }

    public static Boolean getBool(String key, Boolean defaultValue) {
        if (Preference.contains(key)) {
            return Preference.get(key, Boolean.class);
        }
        return defaultValue;
    }

    public static void saveInt(String key, Integer value) {
        Preference.put(key, value);
    }

    public static Integer getInt(String key, Integer defaultValue) {
        if (Preference.contains(key)) {
            return Preference.get(key, Integer.class);
        }
        return defaultValue;
    }

    public static void saveLong(String key, Long value) {
        Preference.put(key, value);
    }

    public static Long getLong(String key, Long defaultValue) {
        if (Preference.contains(key)) {
            return Preference.get(key, Long.class);
        }
        return defaultValue;
    }

    public static void saveString(String key, String value) {
        Preference.put(key, value);
    }

    public static String getString(String key, String defaultValue) {
        if (Preference.contains(key)) {
            return Preference.get(key, String.class);
        }
        return defaultValue;
    }

    public static <T> DmCache<T> getCache(String key, Class<T> a) {
        if (Preference.contains(key)) {
            return Preference.get(key, DmCache.class, a);
        }
        return null;
    }

    public static <T> DmCache<T> getCache(String key, Type a) {
        if (Preference.contains(key)) {
            return Preference.get(key, DmCache.class, a);
        }
        return null;
    }

    public static <T> DmCache<T> getCacheList(String key, Class<T> a) {
        if (Preference.contains(key)) {
            return Preference.getList(key, DmCache.class, a);
        }
        return null;
    }

    public static <T> DmCache<T> getCacheList(String key, Type a) {
        if (Preference.contains(key)) {
            return Preference.getList(key, DmCache.class, a);
        }
        return null;
    }

    public static <T> void putCache(String key, DmCache<T> a) {
        Preference.put(key, a);
    }

    public static void putEncrypt(String key, SecretKey secretKey, Object object) {
        Preference.putEncrypt(key, secretKey, object);
    }

    public static <T> T getDecrypt(@NonNull String key, @NonNull SecretKey secretKey, Class<T> a) {
        return Preference.getDecrypt(key, secretKey, a);
    }
}
