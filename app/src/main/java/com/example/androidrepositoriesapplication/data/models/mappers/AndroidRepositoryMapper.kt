package com.example.androidrepositoriesapplication.data.models.mappers

import com.example.androidrepositoriesapplication.data.models.api_models.AndroidRepositoryDto
import com.example.androidrepositoriesapplication.data.models.database_models.AndroidRepositoryEntity
import com.example.androidrepositoriesapplication.domain.domain_models.AndroidRepositoryItem

// This file contains the mapper functions between the different type of models

// The mapper from api model to database model
fun AndroidRepositoryDto.toAndroidRepositoryEntity(): AndroidRepositoryEntity {
    return AndroidRepositoryEntity(
        remoteRecordId = id,
        name = name ?: "",
        ownerLogin = owner.login ?: "",
        stargazersCount = stargazersCount,
        description = description ?: "",
        createdAt = createdAt ?: "",
        updatedAt = updatedAt ?: "",
        htmlUrl = htmlUrl ?: ""
    )
}

// the mapper from the database model to the domain model
fun AndroidRepositoryEntity.toAndroidRepositoryItem(): AndroidRepositoryItem {
    return AndroidRepositoryItem(
        id = id,
        name = name,
        ownerLogin = ownerLogin,
        stargazersCount = stargazersCount,
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt,
        htmlUrl = htmlUrl
    )
}