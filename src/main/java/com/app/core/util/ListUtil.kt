package com.app.core.util

import com.app.core.action.Action2
import com.app.core.action.Func1
import com.app.core.parser.Parser
import java.util.*

inline fun <reified T> List<*>.asListOfType(): List<T>? =
        if (all { it is T })
            @Suppress("UNCHECKED_CAST")
            this as List<T> else
            null

fun <T> List<T?>?.compare(other: List<T?>?): Boolean {
    if (this == other) {
        return true
    } else if (this != null && other != null && this.size == other.size) {
        for (i in this.indices) {
            if (!this[i].compare(other[i])) return false
        }
        return true
    }
    return false
}

fun <T> T?.compare(other: T?): Boolean {
    return this?.equals(other) ?: false
}

object ListUtil {

    fun <T, E> select(list: List<T>, action: Func1<E, T>): List<E> {
        val result = ArrayList<E>()
        if (list.isNotEmpty()) {
            for (item in list) {
                result.add(action.invoke(item))
            }
        }
        return result
    }

    fun <T> forEach(list: List<T>, action: Action2<T, Int>) {
        if (list.isNotEmpty()) {
            var i = 0
            for (item in list) {
                action.invoke(item, i)
                i++
            }
        }
    }

    fun <T> find(list: List<T>, action: Func1<Boolean, T>): T? {
        if (list.isNotEmpty()) {
            for (item in list) {
                if (action.invoke(item)) {
                    return item
                }
            }
        }
        return null
    }

    fun <T> find(list: List<T>, action: Func1<Boolean, T>, handle: Action2<T, Int>?): T? {
        if (list.isNotEmpty()) {
            var i = 0
            for (item in list) {
                if (action.invoke(item)) {
                    handle?.invoke(item, i)
                    return item
                }
                i++
            }
        }
        return null
    }

    fun <T> findPosition(list: List<T>, action: Func1<Boolean, T>): Int {
        var i = -1
        if (list.isNotEmpty()) {
            i = 0
            for (item in list) {
                if (action.invoke(item)) {
                    return i
                }
                i++
            }
        }
        return i
    }

    fun <T> where(list: List<T>, action: Func1<Boolean, T>): List<T> {
        val result = ArrayList<T>()
        if (list.isNotEmpty()) {
            for (item in list) {
                if (action.invoke(item)) {
                    result.add(item)
                }
            }
        }
        return result
    }

    fun <T> compare(obj: T?, obj1: T?): Boolean {
        if (null != obj) {
            val str = Parser.toJson(obj)
            val str1 = Parser.toJson<T>(obj1)
            return str.equals(str1, ignoreCase = true)
        } else {
            return null == obj1
        }
    }

    fun <T> compareList(cu: List<T>?, next: List<T>?): Boolean {
        if ((cu == null || cu.isEmpty()) && (next == null || next.isEmpty())) {
            return true
        } else if (cu != null && next != null && cu.size == next.size) {
            for (i in cu.indices) {
                if (!compare(cu[i], next[i])) {
                    return false
                }
            }
            return true
        }
        return false
    }
}
