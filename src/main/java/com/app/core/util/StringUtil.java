package com.app.core.util;

import android.support.annotation.NonNull;

public class StringUtil {
    public static boolean isAlpha(@NonNull String str) {
        return str.matches("[a-zA-Z]+");
    }

    public static boolean empty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean empty(String str, String str1) {
        return empty(str) || empty(str1);
    }

    public static boolean empty(String str, String str1, String str2) {
        return empty(str) || empty(str1) || empty(str2);
    }

    public static boolean empty(String str, String str1, String str2, String str3) {
        return empty(str) || empty(str1) || empty(str2) || empty(str3);
    }

    public static boolean emptyTrim(String str) {
        return empty(str) || str.trim().isEmpty();
    }

    public static boolean emptyTrim(String str, String str1) {
        return emptyTrim(str) || emptyTrim(str1);
    }

    public static boolean emptyTrim(String str, String str1, String str2) {
        return emptyTrim(str) || emptyTrim(str1) || emptyTrim(str2);
    }

    public static boolean emptyTrim(String str, String str1, String str2, String str3) {
        return emptyTrim(str) || emptyTrim(str1) || emptyTrim(str2) || emptyTrim(str3);
    }

    /**
     * @param source full string
     * @param in     find string
     * @return int array 2 : 1.start 2.end
     */
    public static int[] range(@NonNull String source, @NonNull String in) {
        int[] range = new int[2];
        range[0] = -1;
        range[1] = -1;
        if (!emptyTrim(source, in)) {
            range[0] = source.indexOf(in, 0);
            if (range[0] != -1) range[1] = range[0] + in.length();
        }
        return range;
    }

    /**
     * @param source full string
     * @param in     find string
     * @param start  startIndex
     * @return int array 2 : 1.start 2.end
     */
    public static int[] range(@NonNull String source, @NonNull String in, int start) {
        int[] range = new int[2];
        range[0] = -1;
        range[1] = -1;
        if (!emptyTrim(source, in)) {
            range[0] = source.indexOf(in, start);
            if (range[0] != -1) range[1] = range[0] + in.length();
        }
        return range;
    }

    /**
     * @param source full string
     * @param in     find string
     * @return int array 2 : 1.start 2.end
     */
    public static int[] rangeLast(@NonNull String source, @NonNull String in) {
        int[] range = new int[2];
        range[0] = -1;
        range[1] = -1;
        if (!emptyTrim(source, in)) {
            range[0] = source.lastIndexOf(in);
            if (range[0] != -1) range[1] = range[0] + in.length();
        }
        return range;
    }
}
