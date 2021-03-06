package com.rolemodel.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import com.jakewharton.rxbinding3.InitialValueObservable
import com.rolemodel.data.model.Status
import com.rolemodel.data.repository.DataRepository
import com.rolemodel.scheduler.SchedulerProvider
import com.rolemodel.ui.base.BaseViewModel
import com.rolemodel.ui.base.ScreenState
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

class SearchViewModel constructor(
    private val repository: DataRepository,
    private val scheduler: SchedulerProvider
) : BaseViewModel() {
    private val searchLiveData = MutableLiveData<ScreenState<SearchState>>()

    val searchState = searchLiveData.distinctUntilChanged()

    fun searchArtists(searchTerm: String) {
        searchLiveData.postValue(ScreenState.Loading)

        if (searchTerm.length <= 2) {
            searchLiveData.postValue(ScreenState.Success(SearchState.ShowInitial))
            return
        }

        repository
            .searchArtists(searchTerm)
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe(Consumer {
                it?.artists?.let { safeArtists ->
                    safeArtists.persons?.let { safePersons ->
                        if (safePersons.isNotEmpty())
                            searchLiveData.postValue(
                                ScreenState.Success(SearchState.ShowPersons(safePersons)))
                        else
                            searchLiveData.postValue(
                                ScreenState.Success(SearchState.ShowEmpty))
                    }
                }
            }, defaultErrors)
            .let { compositeDisposable.add(it) }
    }

    fun registerSearchListener(searchObservable: InitialValueObservable<CharSequence>) {
        searchObservable
            .debounce(500, TimeUnit.MILLISECONDS)
            .filter {
                if (it.length <= 2)
                    searchLiveData.postValue(ScreenState.Success(SearchState.ShowInitial))
                it.length > 2
            }
            .switchMapSingle {
                repository.searchArtists(it.toString())
                    .doOnSubscribe {
                        searchLiveData.postValue(ScreenState.Loading)
                    }
                    .retry(3)
            }
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe(Consumer {
                it?.artists?.let { safeArtists ->
                    safeArtists.persons?.let { safePersons ->
                        if (safePersons.isNotEmpty())
                            searchLiveData.postValue(
                                ScreenState.Success(SearchState.ShowPersons(safePersons)))
                        else
                            searchLiveData.postValue(
                                ScreenState.Success(SearchState.ShowEmpty))
                    }
                }
            }, defaultErrors)
            .let { compositeDisposable.add(it) }
    }

    override fun onError(status: Status) =
        searchLiveData.postValue(ScreenState.Error(status))
}