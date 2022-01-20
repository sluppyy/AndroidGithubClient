package my.projects.githubclient.model.respository

import kotlinx.coroutines.flow.Flow
import my.projects.githubclient.model.data.User

interface GithubRepository {
    suspend fun getUser(user: String, onError: (String) -> Unit = {}): Flow<User?>
}