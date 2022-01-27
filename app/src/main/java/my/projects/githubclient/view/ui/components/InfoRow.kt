package my.projects.githubclient.view.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun InfoRow(
    title: String,
    advancedInfo: String,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit
) {
    Card(modifier = modifier) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically){
            icon()
            Text(text = title, modifier = Modifier.padding(horizontal = 8.dp).weight(1f))
            Text(text = advancedInfo, modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}