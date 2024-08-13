package com.example.androidrepositoriesapplication.ui.main

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.androidrepositoriesapplication.ui.details.viewmodel.DetailsViewModel
import com.example.androidrepositoriesapplication.ui.repositories.RepositoriesScreen
import com.example.androidrepositoriesapplication.ui.repositories.viewmodel.RepositoriesViewModel
import com.example.androidrepositoriesapplication.ui.splash.SplashScreen
import com.example.androidrepositoriesapplication.ui.splash.viewmodel.SplashViewModel
import com.example.androidrepositoriesapplication.ui.theme.AndroidRepositoriesApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidRepositoriesApplicationTheme {
                val configuration = LocalConfiguration.current
                val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
                val splashViewModel = hiltViewModel<SplashViewModel>()
                val repositoriesViewModel = hiltViewModel<RepositoriesViewModel>()
                val detailsViewModel = hiltViewModel<DetailsViewModel>()
                val isSplashShown by splashViewModel.isSplashShown.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // show the splash screen as long as the data are fetched from the server
                    if (isSplashShown) {
                        SplashScreen(viewModel = splashViewModel)
                    } else {
                        if (!isLandscape) {
                            PortraitLayout(
                                repositoriesViewModel = repositoriesViewModel,
                                detailsViewModel = detailsViewModel
                            )
                        } else {
                            LandScapeLayout(
                                repositoriesViewModel = repositoriesViewModel,
                                detailsViewModel = detailsViewModel
                            )
                        }
                    }

                }
            }
        }
    }
}