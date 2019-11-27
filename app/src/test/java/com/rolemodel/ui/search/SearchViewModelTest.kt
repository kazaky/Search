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
    fun `test searching artists with not enough characters should emit initial state`() {
        `when`(repo.searchArtists(sampleArtist.name!!))
                .thenReturn(Single.just(
                        SearchResponse(Artists(listOf()))))

        vm.searchArtists("Ba")

        when (val screenState = vm.searchState.observedValue()) {
            is ScreenState.Success -> assertTrue(screenState.data is SearchState.ShowInitial)
        }
    }

    @Test
    fun `test searching artists no results found should emit no results`() {
        `when`(repo.searchArtists(sampleArtist.name!!))
                .thenReturn(Single.just(
                        SearchResponse(Artists(listOf()))))

        vm.searchArtists(sampleArtist.name!!)

        when (val screenState = vm.searchState.observedValue()) {
            is ScreenState.Success -> assertTrue(screenState.data is SearchState.ShowEmpty)
        }
    }

    @Test
    fun `test searching artists should emit success state`() {
        `when`(repo.searchArtists(sampleArtist.name!!))
                .thenReturn(Single.just(
                        SearchResponse(Artists(listOf(sampleArtist)))))

        vm.searchArtists(sampleArtist.name!!)

        assert(vm.searchState.observedValue() is ScreenState.Success)
    }

    @Test
    fun `test searching artists should emit success state with exact value`() {
        `when`(repo.searchArtists(sampleArtist.name!!))
                .thenReturn(Single.just(
                        SearchResponse(Artists(listOf(sampleArtist)))))

        vm.searchArtists(sampleArtist.name!!)

        when (val screenState = vm.searchState.observedValue()) {
            is ScreenState.Success ->
                when (val searchState = screenState.data) {
                    is SearchState.ShowPersons ->
                        assert(searchState.persons[0].name == sampleArtist.name)
                }
        }
    }

    @Test
    fun `test searching artists failure should emit error state`() {
        `when`(repo.searchArtists(sampleArtist.name!!))
                .thenReturn(Single.error(IOException()))

        vm.searchArtists(sampleArtist.name!!)

        assert(vm.searchState.observedValue() is ScreenState.Error)
    }
}