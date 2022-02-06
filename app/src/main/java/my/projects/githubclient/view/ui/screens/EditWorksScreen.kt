package my.projects.githubclient.view.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.projects.githubclient.R
import my.projects.githubclient.utils.SelectableObject
import my.projects.githubclient.utils.not
import my.projects.githubclient.utils.toBoolean
import my.projects.githubclient.view.data.*
import my.projects.githubclient.view.ui.components.WorkDraw

@Composable
fun EditWorksScreen(
    modifier: Modifier = Modifier,
    works: List<SelectableObject<Work>>,
    onEditWork: (SelectableObject<Work>) -> Unit = {},
    onBackStack: () -> Unit = {}
) {
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
                        text = "Edit",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            works.forEach { work -> item {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Checkbox(
                        checked = work.toBoolean(),
                        onCheckedChange = {
                            onEditWork(!work) },
                        modifier = Modifier
                            .size(50.dp)
                            .padding(6.dp)
                    )

                    WorkDraw(
                        work = work.obj,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(40.dp)
                    )
                }
            } }
        }
    }
}