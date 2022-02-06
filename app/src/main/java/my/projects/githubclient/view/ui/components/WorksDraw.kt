package my.projects.githubclient.view.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import my.projects.githubclient.R
import my.projects.githubclient.utils.SelectableObject
import my.projects.githubclient.view.data.Work


@Composable
fun WorksDraw(
    modifier: Modifier = Modifier,
    works: Iterable<SelectableObject<Work>>,
    onWorkClick: (Work) -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "My Work",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )

            IconButton(
                onClick = onEditClick,
                modifier = Modifier.size(40.dp).padding(8.dp)
            ){
                Icon(
                    painterResource(id = R.drawable.ic_outline_menu),
                    "",
                )
            }
        }

        works.forEach { work ->
            WorkDraw(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(40.dp),
                work = work.obj,
                onWorkClick = onWorkClick
            )
        }
    }
}