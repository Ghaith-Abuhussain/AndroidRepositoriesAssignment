package com.example.androidrepositoriesapplication.data.models.api_models

import com.google.gson.annotations.SerializedName

// This the data class that represents the response body of our api (if the response code is 200)
data class AndroidRepositoriesResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val items: List<AndroidRepositoryDto>
)

data class AndroidRepositoryDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String? = null,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("stargazers_count") val stargazersCount: Int,
    @SerializedName("description") val description: String? = null,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
    @SerializedName("html_url") val htmlUrl: String? = null,
)

data class Owner(
    @SerializedName("login") var login: String? = null,
)