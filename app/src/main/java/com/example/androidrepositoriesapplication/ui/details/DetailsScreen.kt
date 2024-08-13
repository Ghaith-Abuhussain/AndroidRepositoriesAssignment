package com.example.androidrepositoriesapplication.ui.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.example.androidrepositoriesapplication.ui.details.composables.ChipListComposable
import com.example.androidrepositoriesapplication.ui.details.composables.IconTitleDetails
import com.example.androidrepositoriesapplication.ui.details.viewmodel.DetailsViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreen(id: Long, viewModel: DetailsViewModel) {
    val scope = rememberCoroutineScope()
    val details by viewModel.details.observeAsState()

    // The variables that represent the states of the Chiplist and which FilterChip is selected or not
    val isStarsFilterChipSelected = rememberSaveable{ mutableStateOf(true) }
    val isDescriptionFilterChipSelected = rememberSaveable{mutableStateOf(true)}
    val isLifeTimeFilterChipSelected = rememberSaveable{mutableStateOf(true)}

    // fetch the details from the local db by id
    LaunchedEffect(id) {
        scope.launch {
            viewModel.getDetails(id)
        }
    }

    // to navigate to the repository by the browser
    val uriHandler = LocalUriHandler.current

    // if no details are fetched yet show CircularProgressIndicator in the center
    if (details == null) {
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        // The details are fetched
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
            ChipListComposable(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 30.dp),
            isStarsFilterChipSelected = isStarsFilterChipSelected.value,
            isDescriptionFilterChipSelected = isDescriptionFilterChipSelected.value,
            isLifeTimeFilterChipSelected = isLifeTimeFilterChipSelected.value,
            onClickStars = { isStarsFilterChipSelected.value = !isStarsFilterChipSelected.value },
            onClickDescription = { isDescriptionFilterChipSelected.value = !isDescriptionFilterChipSelected.value },
            onClickLifeTime = { isLifeTimeFilterChipSelected.value = !isLifeTimeFilterChipSelected.value }
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    Text(
                        details!!.name,
                        modifier = Modifier.padding(bottom = 20.dp),
                        style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary)
                    )
                    IconTitleDetails(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 20.dp),
                        icon = Icons.Filled.Person,
                        title = "OWNER:",
                        content = details!!.ownerLogin
                    )
                    // Show some animations ^-^
                    AnimatedVisibility(visible = isStarsFilterChipSelected.value,
                        enter = fadeIn(
                            // Fade in with the initial alpha of 0.3f.
                            initialAlpha = 0.3f
                        ),
                        exit = fadeOut()) {
                        IconTitleDetails(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            icon = Icons.Filled.Stars,
                            title = "STARS:",
                            content = details!!.stargazersCount.toString()
                        )
                    }
                    AnimatedVisibility(visible = isDescriptionFilterChipSelected.value,
                        enter = fadeIn(
                            // Fade in with the initial alpha of 0.3f.
                            initialAlpha = 0.3f
                        ),
                        exit = fadeOut())  {
                        Text(
                            text = details!!.description,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
                        )
                    }
                    AnimatedVisibility(visible = isLifeTimeFilterChipSelected.value,
                        enter = fadeIn(
                            // Fade in with the initial alpha of 0.3f.
                            initialAlpha = 0.3f
                        ),
                        exit = fadeOut()) {
                        IconTitleDetails(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            icon = Icons.Filled.DateRange,
                            title = "Life Time (Days):",
                            content = ChronoUnit.DAYS.between(
                                LocalDateTime.parse(
                                    details!!.createdAt,
                                    DateTimeFormatter.ISO_DATE_TIME
                                ),
                                LocalDateTime.parse(
                                    details!!.updatedAt,
                                    DateTimeFormatter.ISO_DATE_TIME
                                )
                            ).toString()
                        )
                    }

                    ElevatedButton(
                        onClick = {
                            println(details!!.htmlUrl)
                            uriHandler.openUri(details!!.htmlUrl)
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(text = "GO TO REPOSITORY")
                    }
                }
            }
        }
    }
}