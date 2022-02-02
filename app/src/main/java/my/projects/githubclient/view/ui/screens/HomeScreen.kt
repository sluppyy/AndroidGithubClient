package my.projects.githubclient.view.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import my.projects.githubclient.view.data.Work
import my.projects.githubclient.view.ui.components.WorksDraw
import my.projects.githubclient.viewmodel.ProfileViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSearchClick: () -> Unit = {},
    onAddIssue: () -> Unit = {},
    onWorkClick: (Work) -> Unit = {},
    isUpdating: Boolean = false,
    onUpdate: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HomeTopBar(
                onSearchClick = onSearchClick,
                onAddIssue = onAddIssue
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
                        works = Work.values().asList(),
                        onWorkClick = onWorkClick,
                        modifier = Modifier.fillMaxWidth()
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

            IconButton(
                onClick = onAddIssue,
                modifier = Modifier
                    .size(40.dp)
                    .padding(horizontal = 8.dp)
            ) {
                Icon(painterResource(id = R.drawable.ic_outline_add_circle_outline_24),
                    contentDescription = "",
                    tint = onBackgroundColor)
            }
        }
    }
}