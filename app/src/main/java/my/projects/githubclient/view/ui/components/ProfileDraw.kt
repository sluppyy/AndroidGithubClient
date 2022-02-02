package my.projects.githubclient.view.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import my.projects.githubclient.R
import my.projects.githubclient.model.data.User

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileDraw(
    user: User?,
    modifier: Modifier = Modifier
) {
    val profilePictureModifier = Modifier
        .size(80.dp)
        .padding(8.dp)

    val fillMaxWidth = Modifier.fillMaxWidth()

    val profilePainter = rememberImagePainter(
        data = user?.avatar_url ?: "",
        builder = {
            placeholder(R.drawable.ic_outline_person_24)
            error(R.drawable.ic_outline_person_24)
            transformations(CircleCropTransformation())
        }
    )

    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(10.dp)){
            Row(fillMaxWidth, verticalAlignment = Alignment.CenterVertically){
                Image(
                    painter = profilePainter,
                    contentDescription = "Profile picture",
                    modifier = profilePictureModifier)


                Text(user?.login ?: "Login", fontSize = 30.sp)
            }

            Row(fillMaxWidth, verticalAlignment = Alignment.CenterVertically){
                Icon(Icons.Outlined.Person, "", modifier = Modifier
                    .size(40.dp)
                    .padding(start = 8.dp, end = 8.dp))

                Text(buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {append("${user?.followers ?: 0} ")}
                    append("followers ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {append("• ${user?.following ?: 0} ")}
                    append("following")
                })
            }
        }
    }
}