package com.rolemodel.data.network

import com.rolemodel.data.model.SearchResponse
import com.rolemodel.data.network.Urls.BASE_URL_SEARCH
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IdagioApi {

    @GET(BASE_URL_SEARCH)
    fun searchArtists(@Query("term") searchTerm: String): Single<SearchResponse>
}