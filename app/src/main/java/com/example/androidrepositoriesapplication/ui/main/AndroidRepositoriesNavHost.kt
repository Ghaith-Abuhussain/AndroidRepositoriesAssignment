package com.example.androidrepositoriesapplication.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.androidrepositoriesapplication.common.DetailsPage
import com.example.androidrepositoriesapplication.common.RepositoriesPage
import com.example.androidrepositoriesapplication.ui.details.DetailsScreen
import com.example.androidrepositoriesapplication.ui.details.viewmodel.DetailsViewModel
import com.example.androidrepositoriesapplication.ui.repositories.RepositoriesScreen
import com.example.androidrepositoriesapplication.ui.repositories.viewmodel.RepositoriesViewModel

// This is the navigation host that is used in the portrait mode
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AndroidRepositoriesNavHost(
    detailsId: Long, // the details that must be shown after selecting it from the RepositoriesScreen
    navController: NavHostController,
    modifier: Modifier = Modifier,
    repositoriesViewModel: RepositoriesViewModel,
    detailsViewModel: DetailsViewModel,
    onCardClick: (Long) -> Unit, //This function is used to pass the selected repository id to the Navigation Host to know which details to display
    onChangePage: (String) -> Unit // This function is used to change the Scaffold App bar title in the Portrait mode
) {
    NavHost(
        navController = navController,
        startDestination = RepositoriesPage.route,
        modifier = modifier
    ) {
        composable(route = RepositoriesPage.route) {
            onChangePage(RepositoriesPage.route)
            RepositoriesScreen(viewModel = repositoriesViewModel, onClickCard = { detailsId->
                onCardClick(detailsId)
            })
        }
        composable(route = DetailsPage.route) {
            onChangePage(DetailsPage.route)
            DetailsScreen(
                id = detailsId,
                viewModel = detailsViewModel
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

