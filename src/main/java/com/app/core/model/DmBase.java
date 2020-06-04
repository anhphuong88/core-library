package com.app.core.model;

import com.app.core.parser.Parser;
import com.app.core.util.JsonUtil;
import com.app.core.util.StringUtil;

import org.json.JSONObject;

import java.io.Serializable;

public interface DmBase extends Serializable, Cloneable {
    static <T> T create(String json, Class<T> tClass) {
        if (!StringUtil.emptyTrim(json)) {
            return Parser.fromJson(json, tClass);
        }
        return null;
    }

    static <T> T create(JSONObject json, Class<T> tClass) {
        return create(JsonUtil.INSTANCE.toString(json), tClass);
    }

}
