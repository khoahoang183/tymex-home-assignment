package com.khoahoang183.data.base.shared

import android.content.SharedPreferences

abstract class BaseSharedPreferences {

    lateinit var sharedPreferences: SharedPreferences

    inline operator fun <reified T: Any> get(key: String, defaultValue: T): T =
        if (sharedPreferences.contains(key)) {
            sharedPreferences[key, defaultValue]
        } else {
            defaultValue
        }

    inline operator fun <reified T: Any> set(key: String, value: T) {
        sharedPreferences[key] = value
    }

    fun remove(key: String) {
        sharedPreferences.execute { it.remove(key) }
    }

    fun remove(vararg keys: String) {
        sharedPreferences.execute {
            keys.forEach {
                key -> it.remove(key)
            }
        }
    }

    fun clearAll() {
        sharedPreferences.execute { it.clear() }
    }

    companion object {
        const val VERSION: Int = 2
    }
}