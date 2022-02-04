package my.projects.githubclient.view.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import my.projects.githubclient.R
import my.projects.githubclient.view.data.Work

@Composable
fun WorkScreen(
    modifier: Modifier = Modifier,
    work: Work,
    onBackStack: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
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
                        text = work.toString,
                        //fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        },
        content = content)
}