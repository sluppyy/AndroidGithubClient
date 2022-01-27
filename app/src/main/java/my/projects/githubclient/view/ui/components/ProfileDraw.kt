package my.projects.githubclient.view.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import my.projects.githubclient.model.data.AuthUser

@Composable
fun ProfileDraw(
    user: AuthUser?,
    modifier: Modifier = Modifier
) {
    val fillMaxWidth = Modifier.fillMaxWidth()

    val profilePainter = rememberImagePainter(
        data = user?.avatar_url ?: "",
        builder = {
            transformations(CircleCropTransformation())
        }
    )

    Column(modifier = modifier){
        Row(fillMaxWidth, verticalAlignment = Alignment.CenterVertically){
            Image(
                painter = profilePainter,
                contentDescription = "Profile picture",
                modifier = Modifier.size(96.dp).padding(8.dp))

            Text(user?.login ?: "Login", fontSize = 30.sp)
        }

        Row(fillMaxWidth.padding(top = 6.dp), verticalAlignment = Alignment.CenterVertically){
            Icon(Icons.Default.Person, "", modifier = Modifier.size(20.dp).padding(end = 8.dp))

            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {append("${user?.followers ?: 0} ")}
                append("followers ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {append("• ${user?.following ?: 0} ")}
                append("following")
            })
        }
    }
}