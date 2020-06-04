package com.app.core.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.app.core.constant.DefaultConstants;
import com.app.core.parser.Parser;
import com.app.core.util.StringUtil;
import com.app.core.util.TypeUtil;
import com.app.core.util.UIUtil;

import java.lang.reflect.Type;
import java.util.ConcurrentModificationException;
import java.util.List;

import javax.crypto.SecretKey;

/**
 * Created by PhuongNA2 on 5/19/2017.
 */

class Preference {
    private static SharedPreferences preferences;

    public static void init(Context context) {
        preferences = context.getSharedPreferences(DefaultConstants.Preference.KEY, Context.MODE_PRIVATE);
    }

    public static void put(String key, Object object) {
        if (key == null || key.isEmpty() || object == null) {
            return;
        }
        try {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, Parser.toJson(object));
            editor.apply();
        } catch (ConcurrentModificationException cme) {
            cme.printStackTrace();
        }
    }

    public static void delete(String key) {
        if (key == null || key.isEmpty()) {
            return;
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public static boolean contains(String key) {
        return preferences.contains(key);
    }

    public static <T> T get(String key, Class<T> a) {
        String str = preferences.getString(key, null);
        if (str == null) {
            return null;
        } else {
            try {
                return Parser.fromJson(str, a);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static <T> T get(String key, Type type) {
        String str = preferences.getString(key, null);
        if (str == null) {
            return null;
        } else {
            try {
                return Parser.fromJson(str, type);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static <T, E> E get(String key, Class<?> eClass, Class<T> a) {
        String str = preferences.getString(key, null);
        if (str == null) {
            return null;
        } else {
            try {
                return Parser.fromJson(str, TypeUtil.getType(eClass, a));
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static <E> E get(String key, Class<?> eClass, Type type) {
        String str = preferences.getString(key, null);
        if (str == null) {
            return null;
        } else {
            try {
                return Parser.fromJson(str, TypeUtil.getType(eClass, type));
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static <T> List<T> getList(String key, Class<T> a) {
        String str = preferences.getString(key, null);
        if (str == null) {
            return null;
        } else {
            try {
                return Parser.fromJson(str, TypeUtil.getType(List.class, a));
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static <T> List<T> getList(String key, Type type) {
        String str = preferences.getString(key, null);
        if (str == null) {
            return null;
        } else {
            try {
                return Parser.fromJson(str, TypeUtil.getType(List.class, type));
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static <T, E> E getList(String key, Class<?> eClass, Class<T> a) {
        String str = preferences.getString(key, null);
        if (str == null) {
            return null;
        } else {
            try {
                return Parser.fromJson(str, TypeUtil.getType(eClass, TypeUtil.getType(List.class, a)));
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static <E> E getList(String key, Class<?> eClass, Type type) {
        String str = preferences.getString(key, null);
        if (str == null) {
            return null;
        } else {
            try {
                return Parser.fromJson(str, TypeUtil.getType(eClass, TypeUtil.getType(List.class, type)));
            } catch (Exception e) {
                return null;
            }
        }
    }

    public static <T> void put(String key, List<T> list) {
        if (key == null || key.isEmpty() || list == null) {
            return;
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, Parser.toJson(list));
        editor.apply();
    }

    public static void putEncrypt(String key, SecretKey secretKey, Object object) {
        if (key == null || key.isEmpty() || object == null || secretKey == null) {
            return;
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, UIUtil.encryptMsg(Parser.toJson(object), secretKey));
        editor.apply();
    }

    public static <T> T getDecrypt(@NonNull String key, @NonNull SecretKey secretKey, Class<T> a) {
        String str = preferences.getString(key, null);
        if (str == null) {
            return null;
        } else {
            try {
                String cipherText = UIUtil.decryptMsg(str, secretKey);
                if (!StringUtil.emptyTrim(cipherText)) {
                    return Parser.fromJson(cipherText.trim(), a);
                }
            } catch (Exception e) {
                return null;
            }
            return null;
        }
    }
}
