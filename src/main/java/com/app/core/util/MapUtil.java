package com.app.core.util;

import android.support.annotation.NonNull;

import com.app.core.action.Action2;

import java.util.Iterator;
import java.util.Map;

public final class MapUtil {

    public static void iterate(@NonNull Map mp, @NonNull Action2<Object, Object> handle) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            handle.invoke(pair.getKey(), pair.getValue());
            it.remove();
        }
    }

    public static void read(@NonNull Map mp, @NonNull Action2<Object, Object> handle) {
        for (Object o : mp.entrySet()) {
            Map.Entry pair = (Map.Entry) o;
            handle.invoke(pair.getKey(), pair.getValue());
        }
    }

    public static <T> void iterateKind(@NonNull Map<T, T> mp, @NonNull Action2<T, T> handle) {
        Iterator<Map.Entry<T, T>> it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<T, T> pair = it.next();
            handle.invoke(pair.getKey(), pair.getValue());
            it.remove();
        }
    }

    public static <T> void readKind(@NonNull Map<T, T> mp, @NonNull Action2<T, T> handle) {
        for (Map.Entry<T, T> o : mp.entrySet()) {
            handle.invoke(o.getKey(), o.getValue());
        }
    }
}
