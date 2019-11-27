package com.rolemodel.ui.search

import android.os.Bundle
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
    }

    private fun showEmpty() {
    }

    private fun showLoading(shouldShow: Boolean) {
    }

    private fun showArtists(results: List<Person>) {
    }
}