package com.rolemodel.data.repository

import com.rolemodel.data.model.SearchResponse
import io.reactivex.Single

interface DataRepository {
    fun searchArtists(searchTerm: String): Single<SearchResponse>
}