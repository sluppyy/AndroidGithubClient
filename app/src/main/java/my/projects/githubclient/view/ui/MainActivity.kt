package my.projects.githubclient.view.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import my.projects.githubclient.view.data.Screens
import my.projects.githubclient.view.data.Work
import my.projects.githubclient.view.data.WorkfromString
import my.projects.githubclient.view.ui.components.RepositoriesDraw
import my.projects.githubclient.view.ui.screens.HomeScreen
import my.projects.githubclient.view.ui.screens.ProfileScreen
import my.projects.githubclient.view.ui.screens.WorkScreen
import my.projects.githubclient.view.ui.theme.GithubClientTheme
import my.projects.githubclient.viewmodel.ProfileViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            val isUpdating by profileViewModel.isUpdating.collectAsState()

            val scaffoldState = rememberScaffoldState()
            val screens = Screens.values()

            GithubClientTheme {
                //on new message from ProfileViewModel called snackbar
                LaunchedEffect(scaffoldState.snackbarHostState) {
                    profileViewModel.snackBarMessage.collect { newMessage ->
                        if (newMessage != null) {
                            scaffoldState
                                .snackbarHostState
                                .showSnackbar(resources.getString(newMessage))
                        }
                    }
                }

                Scaffold(
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()

                        Crossfade(
                            targetState = screens.fold(false){ acc, screen ->
                                navBackStackEntry?.destination?.hierarchy?.
                                any { it.route == screen.route} == true || acc
                            }
                        ) { show ->
                            if (show) {
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
                            }
                        }
                    },
                    scaffoldState = scaffoldState,
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = screens[0].route,
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable(Screens.HOME.route) { HomeScreen(
                            modifier = Modifier.fillMaxSize(),
                            onWorkClick = {navController.navigate("work/${it.toString}")},
                            isUpdating = isUpdating,
                            onUpdate = profileViewModel::updateProfile
                        )
                        }

                        composable(Screens.PROFILE.route) { ProfileScreen(
                            viewModel = profileViewModel,
                            modifier = Modifier.fillMaxSize(),
                            onWorkClick = {navController.navigate("work/${it.toString}")},
                            onShareClick = {
                                if (profileViewModel.user.value != null) shareText(profileViewModel.user.value?.html_url!!)
                            },
                            isUpdating = isUpdating,
                            onUpdate = profileViewModel::updateProfile)
                        }

                        composable(
                            "work/{work}",
                            arguments = listOf(navArgument("work"){type = NavType.StringType})
                        ) { backStackEntry ->
                            val work = WorkfromString(backStackEntry.arguments?.getString("work")!!)!!

                            WorkScreen(
                                work = work,
                                onBackStack = {navController.popBackStack()},
                                modifier = Modifier.fillMaxSize()
                            ) {
                                when (work) {
                                    Work.REPOSITORIES -> {
                                        val repos by profileViewModel.repositories.collectAsState()
                                        RepositoriesDraw(repos = repos ?: emptyList())
                                    }
                                    Work.STARRED -> {
                                        val starred by profileViewModel.starred.collectAsState()
                                        RepositoriesDraw(repos = starred ?: emptyList())
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun shareText(message: String) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, "Github profile")
        startActivity(shareIntent)
    }
}