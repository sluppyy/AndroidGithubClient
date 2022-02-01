package my.projects.githubclient.view.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import my.projects.githubclient.R

enum class Screens(
    val route: String,
    @StringRes
    val nameId: Int,
    @DrawableRes
    val iconId: Int
    ) {
    HOME("home", R.string.home_path, R.drawable.ic_outline_home_24),
    PROFILE("profile", R.string.profile_path, R.drawable.ic_outline_person_24)
}