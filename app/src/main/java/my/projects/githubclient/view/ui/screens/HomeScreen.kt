package my.projects.githubclient.view.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.projects.githubclient.R
import my.projects.githubclient.model.data.Work
import my.projects.githubclient.view.ui.components.WorksDraw
import my.projects.githubclient.viewmodel.ProfileViewModel

@Composable
fun HomeScreen(
    viewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HomeTopBar()
        }
    ) {
       LazyColumn(
           modifier = Modifier.fillMaxSize()
       ) {
           //My work (Issues, Pull Requests, Discussions, ...)
           item {
               WorksDraw(
                   works = Work.values().asList(),
                   onWorkClick = {},
                   modifier = Modifier.fillMaxWidth()
               )
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
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("    Home", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.weight(1f))

            Image(
                painterResource(id = R.drawable.ic_outline_search_24),
                "",
                modifier = Modifier.size(50.dp).padding(horizontal = 8.dp).clickable{onSearchClick()})

            Image(
                painterResource(R.drawable.ic_outline_add_circle_outline_24),
                "",
                modifier = Modifier.size(50.dp).padding(horizontal = 8.dp).clickable{onAddIssue()})

        }
    }
}