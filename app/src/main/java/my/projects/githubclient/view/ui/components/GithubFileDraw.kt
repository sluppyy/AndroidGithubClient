package my.projects.githubclient.view.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import my.projects.githubclient.R
import my.projects.githubclient.model.data.GithubFile

@Composable
fun GithubFileDraw(
    file: GithubFile,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                when (file.type) {
                    "dir" -> painterResource(id = R.drawable.ic_folder)
                    else -> {painterResource(id = R.drawable.ic_outline_insert_drive_file_24)}
                },
                "",
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp))

            Text(file.name)
        }
    }
}