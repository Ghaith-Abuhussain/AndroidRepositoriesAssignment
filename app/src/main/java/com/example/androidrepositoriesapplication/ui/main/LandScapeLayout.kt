package com.example.androidrepositoriesapplication.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.androidrepositoriesapplication.ui.details.DetailsScreen
import com.example.androidrepositoriesapplication.ui.details.viewmodel.DetailsViewModel
import com.example.androidrepositoriesapplication.ui.repositories.RepositoriesScreen
import com.example.androidrepositoriesapplication.ui.repositories.viewmodel.RepositoriesViewModel

// This Layout is used in the landscape mode Showing both RepositoriesScreen and DetailsScreen in the same screen
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandScapeLayout(
    modifier: Modifier = Modifier,
    repositoriesViewModel: RepositoriesViewModel,
    detailsViewModel: DetailsViewModel
) {

    var detailsId by rememberSaveable { mutableStateOf<Long>(-1) }
    println("LandScapeLayout is composed with detailsId = $detailsId")
    Scaffold(modifier = modifier, topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text("Android Repositories")
            }
        )
    }) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                RepositoriesScreen(
                    viewModel = repositoriesViewModel,
                    onClickCard = { newDetailsId ->
                        detailsId = newDetailsId
                    }
                )
            }
            if (detailsId < 0) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Nothing to Show Yet")
                }
            } else {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    DetailsScreen(id = detailsId, viewModel = detailsViewModel)
                }
            }
        }
    }
}