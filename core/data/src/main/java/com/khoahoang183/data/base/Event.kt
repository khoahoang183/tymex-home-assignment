package com.khoahoang183.data.base

import java.util.concurrent.ConcurrentHashMap


/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
class Event<T>(content: T) {
    private val mContent: T
    private var hasBeenHandled = false
    private val signature by lazy { ConcurrentHashMap<Int, Boolean>() }

    val contentIfNotHandled: T?
        get() = if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            mContent
        }

    fun getContentIfNotHandled(signatureObject: Any): T? {
        val hasHandle = signature[signatureObject.hashCode()] ?: false
        return if (hasHandle) {
            null
        } else {
            signature[signatureObject.hashCode()] = true
            mContent
        }
    }

    fun signatureHasBeenHandle(signatureObject: Any): Boolean {
        return signature[signatureObject.hashCode()] ?: false
    }

    fun hasBeenHandled(): Boolean {
        return hasBeenHandled
    }

    fun peekContent(): T {
        return mContent
    }

    init {
        mContent = content
    }
}