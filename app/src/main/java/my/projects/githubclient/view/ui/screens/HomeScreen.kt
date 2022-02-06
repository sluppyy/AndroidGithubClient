package my.projects.githubclient.view.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import my.projects.githubclient.R
import my.projects.githubclient.model.data.Repository
import my.projects.githubclient.utils.SelectableObject
import my.projects.githubclient.view.data.Work
import my.projects.githubclient.view.ui.components.WorksDraw

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = {},
    onWorkClick: (Work) -> Unit = {},
    isUpdating: Boolean = false,
    onUpdate: () -> Unit = {},
    onEditClick: () -> Unit = {},
    works: List<SelectableObject<Work>>
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HomeTopBar(
                onSearchClick = onSearchClick
            )
        }
    ) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isUpdating),
            onRefresh = onUpdate
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                //My work (Issues, Pull Requests, Discussions, ...)
                item {
                    WorksDraw(
                        works = works,
                        onWorkClick = onWorkClick,
                        modifier = Modifier.fillMaxWidth(),
                        onEditClick = onEditClick
                    )
                }


            }
        }
    }
}

@Preview
@Composable
fun HomeTopBar(
    onSearchClick: () -> Unit = {},
    onAddIssue: () -> Unit = {}
) {
    val onBackgroundColor = MaterialTheme.colors.onBackground

    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("    Home", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.weight(1f))

            IconButton(
                onClick = onSearchClick,
                modifier = Modifier
                    .size(40.dp)
                    .padding(horizontal = 8.dp)
            ) {
                Icon(painterResource(id = R.drawable.ic_outline_search_24),
                    contentDescription = "",
                    tint = onBackgroundColor)
            }
        }
    }
}