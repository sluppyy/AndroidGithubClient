package my.projects.githubclient.view.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
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
import my.projects.githubclient.R
import my.projects.githubclient.config.GITHUB_GET_TOKEN_URL
import my.projects.githubclient.utils.Selected
import my.projects.githubclient.utils.removeUnselected
import my.projects.githubclient.view.data.Screens
import my.projects.githubclient.view.data.Work
import my.projects.githubclient.view.data.workfromString
import my.projects.githubclient.view.ui.components.RepositoriesDraw
import my.projects.githubclient.view.ui.screens.*
import my.projects.githubclient.view.ui.theme.GithubClientTheme
import my.projects.githubclient.viewmodel.ProfileViewModel
import my.projects.githubclient.viewmodel.SettingsViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val profileViewModel: ProfileViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()

            val selectedWorks by settingsViewModel.selectedWorks.collectAsState()

            val screens = Screens.values()
            val isUpdating by profileViewModel.isUpdating.collectAsState()

            GithubClientTheme {
                //on new message from ProfileViewModel called snackbar
                LaunchedEffect(scaffoldState.snackbarHostState) {
                    profileViewModel.snackBarMessage.collect { newMessage ->
                        when (newMessage) {
                            null -> {}
                            R.string.author_error -> {
                                val result = scaffoldState.snackbarHostState.showSnackbar(
                                    resources.getString(newMessage),
                                    actionLabel = resources.getString(R.string.log_in)
                                )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> navController.navigate("work/${Work.SETTINGS}")
                                }
                            }//cal auth dialog
                            else -> scaffoldState
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
                        composable(Screens.HOME.route) {
                            HomeScreen(modifier = Modifier.fillMaxSize(),
                                onWorkClick = {navController.navigate("work/${it.toString}")},
                                isUpdating = isUpdating,
                                onUpdate = profileViewModel::updateProfile,
                                onEditClick = {navController.navigate("editWorks")},
                                works = selectedWorks.removeUnselected()
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
                            onUpdate = profileViewModel::updateProfile,
                            onSettingsClick = {navController.navigate("work/${Work.SETTINGS}")}
                        )
                        }

                        composable(
                            "work/{work}",
                            arguments = listOf(navArgument("work"){type = NavType.StringType})
                        ) { backStackEntry ->
                            val work = workfromString(backStackEntry.arguments?.getString("work")!!)!!

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
                                    Work.SETTINGS -> SettingsScreen(
                                        settingsViewModel = settingsViewModel,
                                        modifier = Modifier.fillMaxSize(),
                                        onSignOut = { 
                                            settingsViewModel.clearAccessToken()
                                            profileViewModel.clearData()
                                            navController.popBackStack()
                                        },
                                        onGetToken = ::openLink,
                                        onSaveToken = {
                                            settingsViewModel.setAccessToken(it)
                                            profileViewModel.clearData()
                                            navController.popBackStack()
                                        }
                                    )
                                }
                            }
                        }

                        composable("editWorks") {
                            EditWorksScreen(
                                works = selectedWorks,
                                onBackStack = navController::popBackStack,
                                onEditWork = settingsViewModel::editWork
                            )
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

    private fun openLink() {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = Uri.parse(GITHUB_GET_TOKEN_URL)
        }
        val shareIntent = Intent.createChooser(sendIntent, "Get Token")
        startActivity(shareIntent)
    }
}