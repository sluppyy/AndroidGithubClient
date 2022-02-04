package my.projects.githubclient.view.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import my.projects.githubclient.model.data.AccessToken
import my.projects.githubclient.view.ui.components.AuthorizationDraw
import my.projects.githubclient.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    modifier: Modifier = Modifier,
    onSignOut: () -> Unit = {},
    onGetToken: () -> Unit = {},
    onSaveToken: (AccessToken) -> Unit = {}
) {
    val is_auth by settingsViewModel.is_authorized.collectAsState()

    LazyColumn(modifier = modifier) {
        item {
            AuthorizationDraw(
                is_authorized = is_auth,
                onSignOut = onSignOut,
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                onGetToken = onGetToken,
                onSaveToken = onSaveToken
            )
        }
    }
}