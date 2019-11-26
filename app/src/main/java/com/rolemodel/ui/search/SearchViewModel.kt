package com.rolemodel.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import com.rolemodel.data.model.Status
import com.rolemodel.data.repository.DataRepository
import com.rolemodel.scheduler.SchedulerProvider
import com.rolemodel.ui.base.BaseViewModel
import com.rolemodel.ui.base.ScreenState
import io.reactivex.functions.Consumer

class SearchViewModel constructor(
    private val repository: DataRepository,
    private val scheduler: SchedulerProvider
) : BaseViewModel() {
    private val searchLiveData = MutableLiveData<ScreenState<SearchState>>()

    val searchState = searchLiveData.distinctUntilChanged()

    override fun onError(status: Status) =
        searchLiveData.postValue(ScreenState.Error(status))
}