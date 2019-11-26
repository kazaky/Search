package com.rolemodel.ui.base

import com.rolemodel.data.model.Status

sealed class ScreenState<out T : Any> {
    object Loading : ScreenState<Nothing>()
    class Success<out T : Any>(val data: T) : ScreenState<T>()
    class Error(val status: Status) : ScreenState<Nothing>()
}