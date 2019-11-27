package com.rolemodel.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rolemodel.data.model.Artists
import com.rolemodel.data.model.SearchResponse
import com.rolemodel.data.repository.DataRepository
import com.rolemodel.scheduler.TestSchedulerProvider
import com.rolemodel.ui.base.ScreenState
import com.rolemodel.util.MockedData.sampleArtist
import com.rolemodel.util.observedValue
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import java.io.IOException
import kotlin.test.assertTrue

class SearchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repo: DataRepository = Mockito.mock(DataRepository::class.java)
    private val vm: SearchViewModel = SearchViewModel(repo, TestSchedulerProvider())

    @Test
    fun `test searching artists should emit success state`() {
        `when`(repo.searchArtists(sampleArtist.name!!))
                .thenReturn(Single.just(
                        SearchResponse(Artists(listOf(sampleArtist)))))

        vm.searchArtists(sampleArtist.name!!)

        assert(vm.searchState.observedValue() is ScreenState.Success)
    }

    @Test
    fun `test searching artists failure should emit error state`() {
        `when`(repo.searchArtists(sampleArtist.name!!))
                .thenReturn(Single.error(IOException()))

        vm.searchArtists(sampleArtist.name!!)

        assert(vm.searchState.observedValue() is ScreenState.Error)
    }
}