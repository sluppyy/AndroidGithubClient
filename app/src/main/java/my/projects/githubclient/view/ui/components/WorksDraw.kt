package my.projects.githubclient.view.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import my.projects.githubclient.R
import my.projects.githubclient.model.data.Work
import my.projects.githubclient.view.ui.theme.MyColors


@Preview
@Composable
fun WorksDraw(
    modifier: Modifier = Modifier,
    works: Iterable<Work> = Work.values().toList(),
    onWorkClick: (Work) -> Unit = {}
) {
    if (false) TODO("We draw ALL works (need only chosen from config)")

    val imageModifier = Modifier
        .width(40.dp)
        .fillMaxHeight()


    val infoRowModifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .height(40.dp)

    Column(
        modifier = modifier
    ) {
        Text(
            text = "My Work",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        works.forEach { work ->
            when(work) {
                Work.ISSUES -> InfoRow(title = work.toString, "", modifier = infoRowModifier
                    .clickable { onWorkClick(work) }
                ) {
                    IconWithBackground(
                        painter = painterResource(R.drawable.ic_outline_adjust_24),
                        backgroundColor = MyColors.Green, modifier = imageModifier)
                }

                Work.PULL_REQUESTS -> InfoRow(title = work.toString, "", modifier = infoRowModifier
                    .clickable { onWorkClick(work) }
                ){
                    IconWithBackground(
                        painter = painterResource(R.drawable.ic_outline_merge_type_24),
                        backgroundColor = MyColors.Blue, modifier = imageModifier)
                }

                Work.DISCUSSIONS -> InfoRow(title = work.toString, "", modifier = infoRowModifier
                    .clickable { onWorkClick(work) }
                ) {
                    IconWithBackground(
                        painter = painterResource(R.drawable.ic_outline_mark_chat_unread_24),
                        backgroundColor = MyColors.Purple, modifier = imageModifier)
                }

                Work.REPOSITORIES -> InfoRow(title = work.toString, "", modifier = infoRowModifier
                    .clickable { onWorkClick(work) }) {
                    IconWithBackground(
                        painter = painterResource(R.drawable.ic_outline_book_24),
                        backgroundColor = MyColors.Black, modifier = imageModifier)
                }

                Work.ORGANIZATIONS -> InfoRow(title = work.toString, "", modifier = infoRowModifier
                    .clickable { onWorkClick(work) }
                ) {
                    IconWithBackground(
                        painter = painterResource(R.drawable.ic_outline_organisations_24),
                        backgroundColor = MyColors.Orange, modifier = imageModifier)
                }

                Work.STARRED -> InfoRow(title = work.toString, "", modifier = infoRowModifier
                    .clickable { onWorkClick(work) }
                ) {
                    IconWithBackground(
                        painter = painterResource(R.drawable.ic_outline_star_border_24),
                        backgroundColor = MyColors.Yellow, modifier = imageModifier
                    )
                }
            }
        }
    }
}