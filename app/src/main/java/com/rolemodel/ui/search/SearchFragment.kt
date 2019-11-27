package com.rolemodel.ui.search

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.widget.textChanges
import com.rolemodel.R
import com.rolemodel.data.model.Person
import com.rolemodel.data.model.Status
import com.rolemodel.ui.base.BaseFragment
import com.rolemodel.ui.base.ScreenState
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment() {
    private val viewModel: SearchViewModel by viewModel()

    private lateinit var artistsAdapter: ArtistsListAdapter

    override fun getLayoutId(): Int = R.layout.fragment_search

    override fun afterCreation(bundle: Bundle?) {
        // Init UI
        artistsAdapter = ArtistsListAdapter()
        rvDoctors.adapter = artistsAdapter

        // Observing ViewModel state changes
        viewModel.searchState.observe(viewLifecycleOwner, Observer { renderUI(it) })

        // Start listening to search changes
        viewModel.registerSearchListener(etSearch.textChanges())
    }

    private fun renderUI(searchState: ScreenState<SearchState>?) {
        when (searchState) {
            is ScreenState.Loading -> showLoading(true)
            is ScreenState.Success -> processSuccessState(searchState.data)
            is ScreenState.Error -> processErrorState(searchState.status)
        }
    }

    private fun processErrorState(status: Status) {
        showLoading(false)

        activity?.let { safeActivity ->
            Snackbar.make(
                safeActivity.findViewById(android.R.id.content),
                status.message,
                Snackbar.LENGTH_INDEFINITE
            ).setAction(
                getString(R.string.retry)
            ) {
                viewModel.registerSearchListener(etSearch.textChanges())
            }.show()
        }
    }

    private fun processSuccessState(searchState: SearchState) =
        when (searchState) {
            is SearchState.ShowInitial -> showInitial()
            is SearchState.ShowPersons -> showArtists(searchState.persons)
            is SearchState.ShowEmpty -> showEmpty()
        }

    private fun showInitial() {
        rvDoctors.visibility = GONE
        tvEmpty.visibility = GONE
        showLoading(false)
        wInitial.visibility = VISIBLE
    }

    private fun showEmpty() {
        rvDoctors.visibility = GONE
        wInitial.visibility = GONE
        tvEmpty.visibility = VISIBLE
        showLoading(false)
    }

    private fun showLoading(shouldShow: Boolean) {
        progressBar.visibility = if (shouldShow) VISIBLE else View.INVISIBLE
    }

    private fun showArtists(results: List<Person>) {
        wInitial.visibility = GONE
        tvEmpty.visibility = GONE
        rvDoctors.visibility = VISIBLE
        showLoading(false)
        artistsAdapter.submitList(results)
    }
}