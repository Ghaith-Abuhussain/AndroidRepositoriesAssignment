package com.example.androidrepositoriesapplication.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.androidrepositoriesapplication.common.DetailsPage
import com.example.androidrepositoriesapplication.common.RepositoriesPage
import com.example.androidrepositoriesapplication.ui.details.viewmodel.DetailsViewModel
import com.example.androidrepositoriesapplication.ui.repositories.RepositoriesScreen
import com.example.androidrepositoriesapplication.ui.repositories.viewmodel.RepositoriesViewModel

// This layout is used in the Portrait mode showing the Navigation Host with RepositoriesScreen on top
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortraitLayout(
    modifier: Modifier = Modifier,
    repositoriesViewModel: RepositoriesViewModel,
    detailsViewModel: DetailsViewModel
) {
    val navController = rememberNavController()

    // this is the index of the android repository item list clicked by the user in the RepositoriesScreen
    var detailsId by rememberSaveable { mutableStateOf<Long>(0) }
    var currentPage by rememberSaveable { mutableStateOf(RepositoriesPage.route) }

    Scaffold(modifier = modifier, topBar = {
        TopAppBar(
            colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(currentPage)
            }
        )
    }) { paddingValues ->
        AndroidRepositoriesNavHost(
            modifier = Modifier.padding(paddingValues),
            detailsId = detailsId,
            navController = navController,
            repositoriesViewModel = repositoriesViewModel,
            detailsViewModel = detailsViewModel,
            onCardClick = { newDetailsId ->
                detailsId = newDetailsId
                navController.navigateSingleTopTo(DetailsPage.route)
            },
            onChangePage = {
                currentPage = it
            }
        )
    }
}