package com.example.androidrepositoriesapplication.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.ui.graphics.vector.ImageVector

// This interface that represents the app destinations for the navigation host
interface AppDestinations{
    val route:String
}

object RepositoriesPage : AppDestinations {
    override val route = "Repositories"
}

object DetailsPage : AppDestinations {
    override val route = "Details"
}