package com.example.androidrepositoriesapplication.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androidrepositoriesapplication.R
import com.example.androidrepositoriesapplication.ui.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(viewModel: SplashViewModel) {
    val durationMillis = 1000
    var visible by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        while(true) {
            delay(2000)
            visible = !visible
        }
    }
    LaunchedEffect(key1 = true) {
        // Launch the system settings page using ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION intent action
        viewModel.initialization()
    }

    // Splash screen layout
    Surface(
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(
                    animationSpec = keyframes {
                        this.durationMillis = durationMillis
                    },
                ),
                exit = fadeOut(animationSpec = keyframes {
                    this.durationMillis = durationMillis
                })
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier.size(200.dp),
                )
            }
        }
    }
}