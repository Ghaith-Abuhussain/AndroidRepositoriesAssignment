package com.example.androidrepositoriesapplication.domain.domain_models

import com.example.androidrepositoriesapplication.data.models.api_models.Owner

// The android repository domain model which is the model actually shown in the list of repositories
// in the RepositoriesScreen
data class AndroidRepositoryItem(
    val id: Long,
    val name: String,
    val ownerLogin: String,
    val stargazersCount: Int,
    val description: String,
    val createdAt: String,
    val updatedAt: String,
    val htmlUrl: String,
) {
    // This function is used in the search logic of the RepositoriesScreen to see if the corresponding
    // item match the Search Query fot the repository name or the owner name or their first characters
    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCompinations  = listOf(
            "$name",
            "$ownerLogin",
            "${name.first()}",
            "${ownerLogin.first()}"
        )

        return matchingCompinations.any{
            it.contains(query, ignoreCase = true)
        }
    }
}