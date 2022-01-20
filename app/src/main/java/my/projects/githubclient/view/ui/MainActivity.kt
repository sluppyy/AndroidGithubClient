package my.projects.githubclient.view.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint
import my.projects.githubclient.view.ui.theme.GithubClientTheme
import my.projects.githubclient.viewmodel.ProfileViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var rnd: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val user by profileViewModel.user.collectAsState()

            GithubClientTheme {
                Surface(color = MaterialTheme.colors.background) {
                    if (user != null) {
                        Column {
                            Text("login: ${user!!.login}")
                            Text("email: ${user!!.email}")
                            Text("public_repos: ${user!!.public_repos}")
                        }
                    }
                }
            }
        }
    }
}
