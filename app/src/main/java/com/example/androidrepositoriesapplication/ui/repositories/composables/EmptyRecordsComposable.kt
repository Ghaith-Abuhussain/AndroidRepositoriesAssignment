package com.example.androidrepositoriesapplication.ui.repositories.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

// This Composable is shown when no data are fetched from the server (the local db is empty)
// "because of no internet connection for example"
@Composable
fun EmptyRecordsComposable(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text("Nothing to display yet\nPlease refresh !!!", modifier = Modifier.padding(bottom = 20.dp), style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center))
        IconButton(onClick = onClick, modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(50))) {
            Icon(imageVector = Icons.Filled.Refresh, contentDescription = "")
        }
    }
}