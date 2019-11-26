package com.rolemodel.data.repository

import com.rolemodel.data.model.SearchResponse
import com.rolemodel.data.network.IdagioApi
import io.reactivex.Single

class DataRepositoryImpl constructor(
        private val api: IdagioApi
) : DataRepository {
    override fun searchArtists(searchTerm: String): Single<SearchResponse> {
        return api.searchArtists(searchTerm)
    }
}