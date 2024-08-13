package com.example.androidrepositoriesapplication.ui.repositories.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.androidrepositoriesapplication.common.OrderingOptions

// The radio Group responsible of changing the ordering type of the item in the list
@Composable
fun OrderingRadioGroup(
    orderingType: OrderingOptions,
    modifier: Modifier = Modifier,
    onClick: (orderingOptions: OrderingOptions) -> Unit
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Choose Order", style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(selected = orderingType == OrderingOptions.NO_ORDER, onClick = {
                    onClick(OrderingOptions.NO_ORDER)
                })
                Text(text = "No Order", style = MaterialTheme.typography.labelMedium)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(selected = orderingType == OrderingOptions.ASCENDING, onClick = {
                    onClick(OrderingOptions.ASCENDING)
                })
                Text(text = "Ascending", style = MaterialTheme.typography.labelMedium)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(selected = orderingType == OrderingOptions.DESCENDING, onClick = {
                    onClick(OrderingOptions.DESCENDING)
                })
                Text(text = "Descending", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}