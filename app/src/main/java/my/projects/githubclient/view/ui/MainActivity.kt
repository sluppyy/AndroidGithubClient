package my.projects.githubclient.view.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import my.projects.githubclient.view.ui.components.ProfileDraw
import my.projects.githubclient.view.ui.screens.HomeScreen
import my.projects.githubclient.view.ui.screens.ProfileScreen
import my.projects.githubclient.view.ui.theme.GithubClientTheme
import my.projects.githubclient.viewmodel.ProfileViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubClientTheme {
                HomeScreen(viewModel = profileViewModel, modifier = Modifier.fillMaxSize())
            }
        }
    }
}