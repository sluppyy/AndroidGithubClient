package my.projects.githubclient.view.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import my.projects.githubclient.R
import my.projects.githubclient.view.data.Work
import my.projects.githubclient.view.ui.components.IconWithBackground
import my.projects.githubclient.view.ui.components.InfoRow
import my.projects.githubclient.view.ui.components.ProfileDraw
import my.projects.githubclient.view.ui.theme.MyColors
import my.projects.githubclient.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    modifier: Modifier = Modifier,
    onWorkClick: (Work) -> Unit = {},
    onShareClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    val user by viewModel.user.collectAsState()
    val repos by viewModel.repositories.collectAsState()
    val orgs by viewModel.organisations.collectAsState()
    val starred by viewModel.starred.collectAsState()

    val infoRowModifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .height(40.dp)

    val imageModifier = Modifier
        .width(40.dp)
        .fillMaxHeight()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(
                        onClick = onShareClick,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(horizontal = 8.dp)
                    ) {
                        Image(painterResource(id = R.drawable.ic_outline_share_24),
                            contentDescription = "")
                    }

                    IconButton(
                        onClick = onSettingsClick,
                        modifier = Modifier
                            .size(40.dp)
                            .padding(horizontal = 8.dp)
                    ) {
                        Image(painterResource(id = R.drawable.ic_outline_settings_24),
                            contentDescription = "")
                    }
                }
            }
        },
        modifier = modifier
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ProfileDraw(user = user?.toUser())

            Divider(color = MaterialTheme.colors.onBackground, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 10.dp)
                .height(1.dp))

            //Repositories
            InfoRow(title = Work.REPOSITORIES.toString,
                repos?.size?.toString() ?: "0",
                modifier = infoRowModifier.clickable { onWorkClick(Work.REPOSITORIES) }
            ) {
                IconWithBackground(
                    painter = painterResource(R.drawable.ic_outline_book_24),
                    backgroundColor = MyColors.Black, modifier = imageModifier)
            }

            //Organisations
            InfoRow(title = Work.ORGANIZATIONS.toString,
                advancedInfo = orgs?.size?.toString() ?: "0",
                modifier = infoRowModifier.clickable { onWorkClick(Work.ORGANIZATIONS) }
            ) {
                IconWithBackground(
                    painter = painterResource(R.drawable.ic_outline_organisations_24),
                    backgroundColor = MyColors.Orange, modifier = imageModifier)
            }

            //Starred
            InfoRow(title = Work.STARRED.toString,
                advancedInfo = starred?.size?.toString() ?: "0",
                modifier = infoRowModifier.clickable { onWorkClick(Work.STARRED) }
            ) {
                IconWithBackground(
                    painter = painterResource(R.drawable.ic_outline_star_border_24),
                    backgroundColor = MyColors.Yellow, modifier = imageModifier)
            }
        }
    }
}