package my.projects.githubclient.view.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import my.projects.githubclient.model.data.Repository

@Composable
fun RepositoriesDraw(
    modifier: Modifier = Modifier,
    repos: List<Repository>,
    onRepositoryClick: (Repository) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(repos) { repository ->
            RepositoryDraw(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                repository = repository,
                onRepositoryClick = onRepositoryClick)
        }
    }
}