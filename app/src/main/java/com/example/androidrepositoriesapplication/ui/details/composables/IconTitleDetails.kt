package com.example.androidrepositoriesapplication.ui.details.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// This Composable is to display the details with icon and title
@Composable
fun IconTitleDetails(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    content: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            modifier = Modifier.size(30.dp),
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            modifier = Modifier.padding(start = 5.dp),
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 18.sp
            )
        )
        Text(
            text = content,
            modifier = Modifier.padding(start = 5.dp),
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 18.sp)
        )
    }
}