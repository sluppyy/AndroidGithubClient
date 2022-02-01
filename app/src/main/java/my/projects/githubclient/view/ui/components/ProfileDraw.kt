package my.projects.githubclient.view.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import my.projects.githubclient.R
import my.projects.githubclient.model.data.User
import my.projects.githubclient.view.ui.theme.MyColors

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileDraw(
    user: User?,
    modifier: Modifier = Modifier
) {
    val profilePictureModifier = Modifier
        .size(96.dp)
        .padding(8.dp)

    val fillMaxWidth = Modifier.fillMaxWidth()

    val profilePainter = rememberImagePainter(
        data = user?.avatar_url ?: "",
        builder = {
            error(R.drawable.ic_offline_mode)
            transformations(CircleCropTransformation())
        }
    )

    Column(modifier = modifier){
        Row(fillMaxWidth, verticalAlignment = Alignment.CenterVertically){
            Image(
                painter = profilePainter,
                contentDescription = "Profile picture",
                modifier = profilePictureModifier)


            Text(user?.login ?: "Login", fontSize = 30.sp)
        }

        Row(fillMaxWidth, verticalAlignment = Alignment.CenterVertically){
            Icon(Icons.Default.Person, "", modifier = Modifier
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