package my.projects.githubclient.view.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.projects.githubclient.R
import my.projects.githubclient.model.data.Repository
import my.projects.githubclient.view.ui.components.RepositoriesDraw
import my.projects.githubclient.viewmodel.SearchReposViewModel

@Composable
fun SearchingScreen(
    searchingViewModel: SearchReposViewModel,
    onBackStack: () -> Unit,
    modifier: Modifier = Modifier,
    onReposClick: (Repository) -> Unit,
) {
    val repos by searchingViewModel.currentSearchedRepos.collectAsState()
    val searchedName by searchingViewModel.searchingName.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = MaterialTheme.colors.background) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = onBackStack) {
                        Icon(
                            painterResource(id = R.drawable.ic_outline_arrow_back_24),
                            contentDescription = "back",
                            tint = MaterialTheme.colors.onBackground,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(8.dp)
                        )
                    }

                    OutlinedTextField(
                        value = searchedName,
                        onValueChange = searchingViewModel::updateSearchingName,
                        modifier = Modifier.weight(1f).padding(2.dp),
                        singleLine = true
                    )

                    IconButton(onClick = searchingViewModel::searchRepos) {
                        Icon(
                            painterResource(id = R.drawable.ic_outline_search_24),
                            contentDescription = "search",
                            tint = MaterialTheme.colors.onBackground,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(8.dp)
                        )
                    }
                }
            }
        },
        modifier = modifier
    ) {
        repos?.let { it1 -> RepositoriesDraw(
            repos = it1,
            onRepositoryClick = onReposClick,
            modifier = Modifier.fillMaxSize()
        ) }
    }
}