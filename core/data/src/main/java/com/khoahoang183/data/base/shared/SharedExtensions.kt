package com.khoahoang183.data.base.shared

import android.content.SharedPreferences


fun SharedPreferences.execute(operation: (SharedPreferences.Editor) -> Unit) {
    with(edit()) {
        operation(this)
        apply()
    }
}

inline operator fun <reified T : Any> SharedPreferences.set(key: String, value: T) =
    when (value) {
        is String -> execute { it.putString(key, value) }
        is Int -> execute { it.putInt(key, value) }
        is Boolean -> execute { it.putBoolean(key, value) }
        is Float -> execute { it.putFloat(key, value) }
        is Long -> execute { it.putLong(key, value) }
        is Double -> execute { it.putString(key, value.toString()) }

        else -> throw UnsupportedOperationException("Not yet implemented")
    }

inline operator fun <reified T : Any> SharedPreferences.get(
    key: String,
    defaultValue: T? = null
): T =
    when (T::class) {
        String::class -> getString(key, defaultValue as? String ?: "") as T
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T
        Long::class -> getLong(key, defaultValue as? Long ?: -1L) as T
        Double::class -> (getString(key, (defaultValue as? String) ?: "-1.0")?.toDoubleOrNull() ?: -1.0) as T

        else -> throw UnsupportedOperationException("Not yet implemented")
    }