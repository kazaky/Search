package com.rolemodel.ui.search

import android.os.Bundle
import android.view.View
import com.rolemodel.R
import com.rolemodel.data.model.Person
import com.rolemodel.ui.base.BaseFragment
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
    }


    private fun showInitial() {
        rvDoctors.visibility = View.GONE
        tvEmpty.visibility = View.GONE
        showLoading(false)
        wInitial.visibility = View.VISIBLE
    }

    private fun showEmpty() {
        rvDoctors.visibility = View.GONE
        wInitial.visibility = View.GONE
        tvEmpty.visibility = View.VISIBLE
        showLoading(false)
    }

    private fun showLoading(shouldShow: Boolean) {
        progressBar.visibility = if (shouldShow) View.VISIBLE else View.INVISIBLE
    }

    private fun showArtists(results: List<Person>) {
        wInitial.visibility = View.GONE
        tvEmpty.visibility = View.GONE
        rvDoctors.visibility = View.VISIBLE
        showLoading(false)
        artistsAdapter.submitList(results)
    }
}