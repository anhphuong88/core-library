package com.app.core.util

import com.app.core.log.Logger
import com.app.core.parser.Parser
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type

object JsonUtil {
    fun toString(jsonObject: JSONObject?): String {
        return jsonObject?.toString() ?: ""
    }

    fun <T> toJsonObject(model: T): JSONObject? {
        val json = Parser.toJson(model)
        return if (!StringUtil.emptyTrim(json)) {
            toJsonObject(json)
        } else null
    }

    fun toJsonObject(jsonString: String): JSONObject? {
        var `object`: JSONObject? = null
        try {
            `object` = JSONObject(jsonString)
        } catch (e: JSONException) {
            Logger.w(if (e.message != null) e.message else "")
        }

        return `object`
    }

    fun toJsonObject(map: Map<*, *>): JSONObject {
        return JSONObject(map)
    }

    fun putJsonObject(jsonObject: JSONObject, name: String, obj: Any) {
        try {
            jsonObject.put(name, obj)
        } catch (e: JSONException) {
            Logger.w(if (e.message != null) e.message else "")
        }

    }

    fun getJSONObject(jsonObject: JSONObject, name: String): JSONObject? {
        if (jsonObject.isNull(name) || jsonObject.length() == 0) {
            return null
        }
        var `object`: JSONObject? = null
        try {
            `object` = jsonObject.getJSONObject(name)
        } catch (e: JSONException) {
            Logger.w(if (e.message != null) e.message else "")
        }

        return `object`
    }

    fun getJSONArray(jsonObject: JSONObject, name: String): JSONArray? {
        if (jsonObject.isNull(name) || jsonObject.length() == 0) {
            return null
        }
        var `object`: JSONArray? = null
        try {
            `object` = jsonObject.getJSONArray(name)
        } catch (e: JSONException) {
            Logger.w(if (e.message != null) e.message else "")
        }

        return `object`
    }

    fun getJSONObject(jsonObject: JSONArray, index: Int): JSONObject? {
        if (jsonObject.length() == 0 || jsonObject.length() < index) {
            return null
        }
        var `object`: JSONObject? = null
        try {
            `object` = jsonObject.getJSONObject(index)
        } catch (e: JSONException) {
            Logger.w(if (e.message != null) e.message else "")
        }

        return `object`
    }

    fun getJSONArray(jsonObject: JSONArray, index: Int): JSONArray? {
        if (jsonObject.length() == 0 || jsonObject.length() < index) {
            return null
        }
        var `object`: JSONArray? = null
        try {
            `object` = jsonObject.getJSONArray(index)
        } catch (e: JSONException) {
            Logger.w(if (e.message != null) e.message else "")
        }

        return `object`
    }

    fun getString(jsonObject: JSONArray, index: Int): String {
        if (jsonObject.length() == 0 || jsonObject.length() < index) {
            return ""
        }
        var `object` = ""
        try {
            `object` = jsonObject.getString(index)
        } catch (e: JSONException) {
            Logger.w(if (e.message != null) e.message else "")
        }

        return `object`
    }

    fun getString(jsonObject: JSONObject, name: String): String {
        if (jsonObject.length() == 0 || jsonObject.isNull(name)) {
            return ""
        }
        var `object` = ""
        try {
            `object` = jsonObject.getString(name)
        } catch (e: JSONException) {
            Logger.w(if (e.message != null) e.message else "")
        }

        return `object`
    }

    fun getInt(jsonObject: JSONArray, index: Int): Int {
        if (jsonObject.length() == 0 || jsonObject.length() < index) {
            return -1
        }
        var `object` = -1
        try {
            `object` = jsonObject.getInt(index)
        } catch (e: JSONException) {
            Logger.w(if (e.message != null) e.message else "")
        }

        return `object`
    }

    fun getInt(jsonObject: JSONObject, name: String): Int {
        if (jsonObject.length() == 0 || jsonObject.isNull(name)) {
            return -1
        }
        var `object` = -1
        try {
            `object` = jsonObject.getInt(name)
        } catch (e: JSONException) {
            Logger.w(if (e.message != null) e.message else "")
        }

        return `object`
    }

    fun isValid(str: String?): Boolean {
        if (!str.isNullOrEmpty()) {
            try {
                JSONObject(str);
            } catch (e: JSONException) {
                try {
                    JSONArray(str)
                } catch (e: JSONException) {
                    return false
                }
            }
            return true
        }
        return false
    }

    fun isValidObject(str: String?): Boolean {
        if (!str.isNullOrEmpty()) {
            try {
                JSONObject(str);
            } catch (e: JSONException) {
                return false
            }
            return true
        }
        return false
    }

    fun <T : String?> T.isJson(): Boolean {
        return JsonUtil.isValid(this)
    }

    inline fun <reified T> from(json: String?, gson: Gson = Gson()): T? = if (isValid(json)) gson.fromJson(json, T::class.java) else null

    fun <T> from(json: String?, type: Type, gson: Gson = Gson()): T? {
        try {
            return if (isValid(json)) gson.fromJson<T>(json, type) else null
        } catch (e: JsonSyntaxException) {
            Logger.w("JsonSyntaxException ${e.message}")
        }
        return null
    }

    fun <T> to(model: T?, gson: Gson = Gson()): String? = model?.let { gson.toJson(model) }

    fun <T> to(model: T?, type: Type, gson: Gson = Gson()): String? = model?.let { gson.toJson(model, type) }
}
