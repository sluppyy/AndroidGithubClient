package my.projects.githubclient.view.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import my.projects.githubclient.R
import my.projects.githubclient.model.data.AccessToken

@Composable
fun AuthorizationDraw(
    modifier: Modifier = Modifier,
    is_authorized: Boolean,
    onSignOut: () -> Unit = {},
    onSaveToken: (AccessToken) -> Unit = {},
    onGetToken: () -> Unit = {}
) {
    var isEditAccessTokenOpen by remember { mutableStateOf(false) }

    Card(modifier = modifier){
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ){
            AnimatedVisibility(visible = isEditAccessTokenOpen) {
                EditAccessToken(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    onSaveToken = {
                        onSaveToken(it)
                        isEditAccessTokenOpen = false
                    },
                    onGetToken = onGetToken,
                    onExit = {isEditAccessTokenOpen = false}
                )
            }

            AnimatedVisibility(visible = !isEditAccessTokenOpen){
                if (is_authorized) {
                    OutlinedButton(onClick = onSignOut)
                    { Text(stringResource(id = R.string.sign_out)) }
                } else {
                    OutlinedButton(onClick = {
                        isEditAccessTokenOpen = true
                    }) { Text(stringResource(id = R.string.log_in)) }
                }
            }
        }
    }
}