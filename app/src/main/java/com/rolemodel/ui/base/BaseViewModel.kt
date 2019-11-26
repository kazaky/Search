package com.rolemodel.ui.base

import androidx.lifecycle.ViewModel
import com.rolemodel.data.model.Status
import com.rolemodel.data.network.utils.CallThrowable
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    protected var defaultErrors: CallThrowable.Errors = object : CallThrowable.Errors() {
        override fun onShowStatus(status: Status) = onError(status)
    }

    abstract fun onError(status: Status)

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}