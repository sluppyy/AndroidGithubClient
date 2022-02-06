package my.projects.githubclient.view.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import my.projects.githubclient.R
import my.projects.githubclient.model.data.Repository
import my.projects.githubclient.view.ui.theme.MyColors

@Composable
fun RepositoryDraw(
    modifier: Modifier = Modifier,
    repository: Repository,
    onRepositoryClick: (Repository) -> Unit = {}
) {
    val profilePainter = rememberImagePainter(
        data = repository.owner.avatar_url,
        builder = {
            placeholder(R.drawable.ic_outline_person_24)
            error(R.drawable.ic_offline_mode)
        }
    )

    Card(modifier = modifier.clickable { onRepositoryClick(repository) }) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.padding(start = 10.dp, top = 8.dp)) {
                Image(
                    painter = profilePainter,
                    contentDescription = "repository picture",
                    modifier = Modifier.size(20.dp))

                Text(
                    repository.owner.login,
                    modifier = Modifier.padding(start = 8.dp))
            }

            Text(
                repository.name,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp))

            if (repository.description != null) Text(
                repository.description!!,
                modifier = Modifier.padding(start = 10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_filled_star),
                    contentDescription = "star",
                    tint = MyColors.Yellow,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(horizontal = 10.dp)
                )
                Text(repository.stargazers_count.toString())

                if (repository.language != null) {
                    Icon(
                        painterResource(id = R.drawable.ic_filled_circle),
                        contentDescription = "language",
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .size(25.dp)
                            .padding(start = 12.dp, end = 4.dp)
                    )
                    Text(repository.language!!)
                }
            }
        }
    }
}