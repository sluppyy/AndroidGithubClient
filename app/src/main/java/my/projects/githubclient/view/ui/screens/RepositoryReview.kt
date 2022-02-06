package my.projects.githubclient.view.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import my.projects.githubclient.R
import my.projects.githubclient.model.data.Repository
import my.projects.githubclient.view.ui.components.EmptyError
import my.projects.githubclient.view.ui.components.GithubFileDraw
import my.projects.githubclient.view.ui.components.OwnerDraw
import my.projects.githubclient.view.ui.components.ProfileDraw
import my.projects.githubclient.viewmodel.RepositoryReviewViewModel

@Composable
fun RepositoryReview(
    repositoryReviewViewModel: RepositoryReviewViewModel,
    onBackStack: () -> Unit,
    modifier: Modifier = Modifier,
    onShareRepository: (Repository) -> Unit
) {
    val currentRepository by repositoryReviewViewModel.currentRepository.collectAsState()
    val currentFileTree by repositoryReviewViewModel.currentFileTree.collectAsState()

    Scaffold(
        modifier = modifier,
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

                    Text(
                        text = currentRepository?.name ?: stringResource(id = R.string.repository_screen),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = {
                        if (currentRepository != null)
                            onShareRepository(currentRepository!!)
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_outline_share_24),
                            contentDescription = "share",
                            tint = MaterialTheme.colors.onBackground,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton({
                        if (!repositoryReviewViewModel.backstack())
                            onBackStack()
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                    ){
                        Icon(
                            painterResource(R.drawable.ic_baseline_keyboard_control_key_24),
                            ""
                        )
                    }
                }
            }

            item {
                OwnerDraw(
                    user = currentRepository?.owner,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
            }

            currentFileTree?.forEach { file ->
                item {
                    GithubFileDraw(
                        file = file,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                if (file.type == "dir")
                                    repositoryReviewViewModel.readFile(file)
                            }
                    )
                }
            }
        }
    }
}