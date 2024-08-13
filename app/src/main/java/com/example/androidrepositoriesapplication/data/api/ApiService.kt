package com.example.androidrepositoriesapplication.data.api

import com.example.androidrepositoriesapplication.data.models.api_models.AndroidRepositoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// This interface represent the Remote API Service and contains the functions that are responsible
// of making Restfull Requests (GET, POST ..etc)
interface ApiService {

    @GET("search/repositories")
    suspend fun getNewAndroidRepositoriesPage(@Query("q") searchType: String, @Query("per_page") perPage: Int, @Query("page") pageIdx: Int): Response<AndroidRepositoriesResponse>

}