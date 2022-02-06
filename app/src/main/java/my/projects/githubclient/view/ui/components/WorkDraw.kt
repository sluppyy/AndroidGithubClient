package my.projects.githubclient.view.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import my.projects.githubclient.R
import my.projects.githubclient.view.data.Work
import my.projects.githubclient.view.ui.theme.MyColors

@Composable
fun WorkDraw(
    modifier: Modifier = Modifier,
    work: Work,
    onWorkClick: (Work) -> Unit = {}
) {
    val imageModifier = Modifier
        .width(40.dp)
        .fillMaxHeight()

    when(work) {
        Work.ISSUES -> InfoRow(title = work.toString, "", modifier = modifier
            .clickable { onWorkClick(work) }
        ) {
            IconWithBackground(
                painter = painterResource(R.drawable.ic_outline_adjust_24),
                backgroundColor = MyColors.Green, modifier = imageModifier)
        }

        Work.PULL_REQUESTS -> InfoRow(title = work.toString, "", modifier = modifier
            .clickable { onWorkClick(work) }
        ){
            IconWithBackground(
                painter = painterResource(R.drawable.ic_outline_merge_type_24),
                backgroundColor = MyColors.Blue, modifier = imageModifier)
        }

        Work.DISCUSSIONS -> InfoRow(title = work.toString, "", modifier = modifier
            .clickable { onWorkClick(work) }
        ) {
            IconWithBackground(
                painter = painterResource(R.drawable.ic_outline_mark_chat_unread_24),
                backgroundColor = MyColors.Purple, modifier = imageModifier)
        }

        Work.REPOSITORIES -> InfoRow(title = work.toString, "", modifier = modifier
            .clickable { onWorkClick(work) }) {
            IconWithBackground(
                painter = painterResource(R.drawable.ic_outline_book_24),
                backgroundColor = MyColors.Black, modifier = imageModifier)
        }

        Work.ORGANIZATIONS -> InfoRow(title = work.toString, "", modifier = modifier
            .clickable { onWorkClick(work) }
        ) {
            IconWithBackground(
                painter = painterResource(R.drawable.ic_outline_organisations_24),
                backgroundColor = MyColors.Orange, modifier = imageModifier)
        }

        Work.STARRED -> InfoRow(title = work.toString, "", modifier = modifier
            .clickable { onWorkClick(work) }
        ) {
            IconWithBackground(
                painter = painterResource(R.drawable.ic_outline_star_border_24),
                backgroundColor = MyColors.Yellow, modifier = imageModifier
            )
        }
    }
}