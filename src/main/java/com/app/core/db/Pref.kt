package com.app.core.db

import android.content.Context
import android.content.SharedPreferences
import com.app.core.constant.DefaultConstants
import com.app.core.ui.view.decryptMsg
import com.app.core.ui.view.encryptMsg
import com.app.core.util.JsonUtil
import com.app.core.util.TypeUtil
import com.google.gson.reflect.TypeToken
import java.util.*
import javax.crypto.SecretKey

object Pref {
    var preferences: SharedPreferences? = null

    fun init(context: Context) {
        preferences = context.getSharedPreferences(DefaultConstants.Preference.KEY, Context.MODE_PRIVATE)
    }

    fun put(key: String?, obj: Any?) {
        if (key == null || key.isEmpty() || obj == null) {
            return
        }
        try {
            val editor = preferences?.edit()
            if (obj is String) {
                editor?.putString(key, obj)
            } else if (obj is Int) {
                editor?.putInt(key, obj)
            } else if (obj is Boolean) {
                editor?.putBoolean(key, obj)
            } else if (obj is Float) {
                editor?.putFloat(key, obj)
            } else if (obj is Long) {
                editor?.putLong(key, obj)
            } else if (obj is Double) {
                editor?.putFloat(key, obj.toFloat())
            } else {
                editor?.putString(key, JsonUtil.to(obj))
            }
            editor?.apply()
        } catch (cme: ConcurrentModificationException) {
            cme.printStackTrace()
        }
    }

    fun delete(key: String?) {
        if (key == null || key.isEmpty()) {
            return
        }
        val editor = preferences?.edit()
        editor?.remove(key)
        editor?.apply()
    }

    fun contains(key: String?): Boolean {
        return preferences?.contains(key) == true
    }

    inline fun <reified T> get(key: String): T? {
        return try {
            val type = object : TypeToken<T>() {}.type
            if ((object : TypeToken<String>() {}.type).typeName == type.typeName) {
                preferences?.getString(key, null) as? T
            } else if ((object : TypeToken<Int>() {}.type).typeName == type.typeName) {
                preferences?.getInt(key, 0) as? T
            } else if ((object : TypeToken<Boolean>() {}.type).typeName == type.typeName) {
                preferences?.getBoolean(key, false) as? T
            } else if ((object : TypeToken<Float>() {}.type).typeName == type.typeName) {
                preferences?.getFloat(key, 0f) as? T
            } else if ((object : TypeToken<Long>() {}.type).typeName == type.typeName) {
                preferences?.getLong(key, 0) as? T
            } else if ((object : TypeToken<Double>() {}.type).typeName == type.typeName) {
                preferences?.getFloat(key, 0f)?.toDouble() as? T
            } else {
                val str = preferences?.getString(key, null)
                if (str == null) null else JsonUtil.from<T>(str)
            }
        } catch (e: Exception) {
            null
        }
    }


    inline fun <reified E, reified T> getGeneric(key: String?): E? {
        val str = preferences?.getString(key, null)

        return str?.let {
            try {
                JsonUtil.from<E>(it, TypeUtil.getType(E::class.java, T::class.java))
            } catch (e: Exception) {
                null
            }
        }
    }

    inline fun <reified T> getList(key: String?): List<T>? {
        return getGeneric<List<T>, T>(key)
    }

    inline fun <reified E, reified T> getGenericList(key: String): E? {
        val str = preferences?.getString(key, null)

        return str?.let {
            try {
                JsonUtil.from<E>(it, TypeUtil.getType(E::class.java, TypeUtil.getType(List::class.java, T::class.java)))
            } catch (e: Exception) {
                null
            }
        }
    }

    fun encrypt(key: String?, secretKey: SecretKey?, obj: Any?) {
        if (key == null || key.isEmpty() || obj == null || secretKey == null) {
            return
        }
        val editor = preferences?.edit()
        if (obj is String) {
            editor?.putString(key, secretKey.encryptMsg(obj))
        } else {
            editor?.putString(key, secretKey.encryptMsg(JsonUtil.to(obj)))
        }
        editor?.apply()
    }

    inline fun <reified T> decrypt(key: String?, secretKey: SecretKey?): T? {
        val str = preferences!!.getString(key, null)
        if (str == null) {
            return null
        } else {
            try {
                val cipherText = secretKey?.decryptMsg(str)
                cipherText?.let {
                    if (!it.isBlank()) {
                        val type = object : TypeToken<T>() {}.type
                        val typeString = object : TypeToken<String>() {}.type
                        if (typeString.typeName == type.typeName) {
                            return it.trim() as T
                        } else {
                            return JsonUtil.from<T>(it.trim())
                        }
                    }
                }
            } catch (e: Exception) {
                return null
            }
            return null
        }
    }
}
