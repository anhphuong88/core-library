package com.app.core.log;

import com.app.core.BuildConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linhdkl on 1/7/17.
 */

public final class Trace {
    private static List<NetLog> lst = new ArrayList<>();

    public static List<NetLog> netLogs() {
        return lst;
    }

    public static boolean enable() {
        return BuildConfig.DEBUG;
    }


    public static void add(NetLog netLog) {
        if (lst != null && enable()) {
            lst.add(netLog);
        }
    }

}
