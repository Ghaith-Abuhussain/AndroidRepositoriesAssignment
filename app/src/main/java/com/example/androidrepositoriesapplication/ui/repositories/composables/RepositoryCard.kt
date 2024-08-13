package com.example.androidrepositoriesapplication.ui.repositories.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FolderShared
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.androidrepositoriesapplication.R
import com.example.androidrepositoriesapplication.domain.domain_models.AndroidRepositoryItem

// The card that holds the Android repository sub details in the RepositoriesScreen
@Composable
fun RepositoryCard(
    isSelected: Boolean,
    androidRepositoryItem: AndroidRepositoryItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card (
        modifier = modifier.padding(all = if(isSelected) 10.dp else 0.dp).clickable {
            onClick()
        },
        elevation = CardDefaults.cardElevation(4.dp)
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.secondary)
                    .padding(all = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                Icon(painter = painterResource(R.mipmap.repo_icon_foreground), tint = Color.Black, modifier = Modifier.size(width = 40.dp, height = 40.dp), contentDescription = "")
                Text(text = androidRepositoryItem.name, modifier = Modifier.padding(start = 10.dp),style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 5.dp)
            ){
                Icon(imageVector = Icons.Filled.Person, tint = MaterialTheme.colorScheme.tertiary, contentDescription = "")
                Text(text = androidRepositoryItem.ownerLogin,  modifier = Modifier.padding(start = 10.dp),style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 5.dp)
            ){
                Icon(imageVector = Icons.Filled.Star, tint = Color.Yellow, contentDescription = "")
                Text(text = "${androidRepositoryItem.stargazersCount}", modifier = Modifier.padding(start = 10.dp),style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary))
            }
        }
    }
}