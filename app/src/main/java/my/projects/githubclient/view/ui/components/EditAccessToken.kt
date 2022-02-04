package my.projects.githubclient.view.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import my.projects.githubclient.R
import my.projects.githubclient.model.data.AccessToken
import java.lang.System.exit

@Composable
fun EditAccessToken(
    modifier: Modifier = Modifier,
    currentUser: String = "",
    onSaveToken: (AccessToken) -> Unit = {},
    onGetToken: () -> Unit = {},
    onExit: () -> Unit = {}
) {
    var user by remember {mutableStateOf(currentUser)}
    var token by remember {mutableStateOf("")}

    val textFieldModifier = Modifier
        .fillMaxWidth()
        .padding(6.dp)

    Column(modifier = modifier) {
        OutlinedTextField(
            value = user,
            onValueChange = {user = it},
            label = {Text(stringResource(id = R.string.user_field))},
            singleLine = true,
            modifier = textFieldModifier
        )
        OutlinedTextField(
            value = token,
            onValueChange = {token = it},
            label = {Text(stringResource(id = R.string.token_field))},
            singleLine = true,
            modifier = textFieldModifier
        )
        Row(modifier = textFieldModifier) {
            OutlinedButton(
                onClick = onGetToken,
                modifier = Modifier.weight(1f).padding(end = 4.dp)
            ) {Text(stringResource(R.string.get_token))}

            OutlinedButton(
                onClick = {onSaveToken(AccessToken(userLogin = user, userToken = token))},
                modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
            ) {Text(stringResource(R.string.save))}

            OutlinedButton(
                onClick = onExit,
                modifier = Modifier.weight(1f).padding(start = 4.dp)
            ) {Text(stringResource(R.string.exit))}
        }
    }
}