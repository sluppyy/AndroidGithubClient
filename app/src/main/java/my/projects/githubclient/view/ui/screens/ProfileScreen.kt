package my.projects.githubclient.view.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import my.projects.githubclient.view.ui.components.InfoRow
import my.projects.githubclient.view.ui.components.ProfileDraw
import my.projects.githubclient.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    modifier: Modifier
) {
    val user by viewModel.user.collectAsState()

    val fillMaxWidth = Modifier.fillMaxWidth()

    Scaffold(modifier = modifier) {
        Column(modifier = Modifier.fillMaxSize()) {
            ProfileDraw(user = user)

            Divider(color = Color.Black, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 10.dp)
                .height(2.dp))

            //Repositories
            InfoRow(
                title = "Repositories",
                advancedInfo = "1", modifier = fillMaxWidth.height(100.dp).padding(16.dp)) {
                Image(
                    Icons.Default.Menu,
                    "1",
                    modifier = Modifier.width(40.dp).fillMaxHeight().background(Color(0xFF8F6249)
                ))}

            //Organisations
            InfoRow(
                title = "Organisations",
                advancedInfo = "0", modifier = fillMaxWidth.height(100.dp).padding(16.dp)) {
                Image(
                    Icons.Default.Home,
                    "0",
                    modifier = Modifier.width(40.dp).fillMaxHeight().background(Color(0xFFFF7043)))}

            //Starred
            InfoRow(
                title = "Starred",
                advancedInfo = "8", modifier = fillMaxWidth.height(100.dp).padding(16.dp)) {
                Image(
                    Icons.Default.Star,
                    "8",
                    modifier = Modifier.width(40.dp).fillMaxHeight().background(Color(0xFFFFCA28)))}
        }
    }
}