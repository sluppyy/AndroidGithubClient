package my.projects.githubclient.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import my.projects.githubclient.view.data.Screens
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
            val navController = rememberNavController()
            val screens = Screens.values()

            GithubClientTheme {
                Scaffold(
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()

                        BottomNavigation(
                            backgroundColor = MaterialTheme.colors.background
                        ) {
                            screens.forEach { screen ->
                                BottomNavigationItem(
                                    icon = {Icon(painterResource(id = screen.iconId), screen.name)},
                                    label = { Text(text = stringResource(id = screen.nameId))}, 
                                    selected = navBackStackEntry?.destination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = { navController.navigate(screen.route) },
                                    alwaysShowLabel = false
                                )
                            }
                            
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = screens[0].route,
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable(Screens.HOME.route) { HomeScreen(
                            modifier = Modifier.fillMaxSize())
                        }

                        composable(Screens.PROFILE.route) { ProfileScreen(
                            viewModel = profileViewModel,
                            modifier = Modifier.fillMaxSize(),
                            onShareClick = {
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, profileViewModel.user.value?.html_url ?: "https://github.com/")
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, "Github profile")
                                startActivity(shareIntent)
                            }
                        )
                        }
                    }
                }
            }
        }
    }
}