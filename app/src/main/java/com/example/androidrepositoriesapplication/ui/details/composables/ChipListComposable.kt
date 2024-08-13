package com.example.androidrepositoriesapplication.ui.details.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// The composable of the Chip list (using FilterChip)
@Composable
fun ChipListComposable(
    modifier: Modifier = Modifier,
    isStarsFilterChipSelected: Boolean,
    isDescriptionFilterChipSelected: Boolean,
    isLifeTimeFilterChipSelected: Boolean,
    onClickStars: () -> Unit,
    onClickDescription: () -> Unit,
    onClickLifeTime: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            20.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        FilterChip(
            onClick = onClickStars,
            label = {
                Text("STARS", style = MaterialTheme.typography.labelMedium)
            },
            selected = isStarsFilterChipSelected,
            leadingIcon = if (isStarsFilterChipSelected) {
                {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Done icon",
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            } else {
                null
            },
        )
        FilterChip(
            onClick = onClickDescription,
            label = {
                Text("DESCRIPTION", style = MaterialTheme.typography.labelMedium)
            },
            selected = isDescriptionFilterChipSelected,
            leadingIcon = if (isDescriptionFilterChipSelected) {
                {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Done icon",
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            } else {
                null
            },
        )
        FilterChip(
            onClick = onClickLifeTime,
            label = {
                Text("LIFE TIME", style = MaterialTheme.typography.labelMedium)
            },
            selected = isLifeTimeFilterChipSelected,
            leadingIcon = if (isLifeTimeFilterChipSelected) {
                {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Done icon",
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            } else {
                null
            },
        )
    }
}