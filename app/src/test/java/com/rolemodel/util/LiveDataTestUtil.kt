package com.rolemodel.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Extension function
 * To observe LiveData changes without a LifeCycleOwner
 *
 * Gets the value from a LiveData object.
 * Once we got a notification via onChanged,
 * we stop observing to prevent memory leaks
 */
@Throws(InterruptedException::class)
fun <T> LiveData<T>.observedValue(): T? {
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) =
            this@observedValue.removeObserver(this)
    }
    this.observeForever(observer)
    return value
}