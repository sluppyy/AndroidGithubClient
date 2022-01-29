package my.projects.githubclient.model.respository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import my.projects.githubclient.model.data.*
import my.projects.githubclient.model.respository.network.NetworkGithubApi
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRepository @Inject constructor(): GithubRepository {
    @Inject lateinit var networkRepository: NetworkGithubApi

    override suspend fun getUser(
        user: String
    ): Flow<RepositoryResponse<User?>> = flow {
        try {
            val networkResponse = networkRepository.getUser(user)

            if (networkResponse.isSuccessful) emit(Ok(networkResponse.body()))
            else emit(UnknownError<User?>(networkResponse.toString()))
        } catch(e: UnknownHostException) { emit(OfflineError<User?>())
        } catch(e: Exception) { emit(UnknownError<User?>(e.toString())) }
    }

    override suspend fun getAuthUser(): Flow<RepositoryResponse<AuthUser?>> = flow {
        try {
            val networkResponse = networkRepository.getAuthUser()

            if (networkResponse.isSuccessful) emit(Ok(networkResponse.body()))
            else emit(UnknownError<AuthUser?>(networkResponse.toString()))
        } catch(e: UnknownHostException) { emit(OfflineError<AuthUser?>())
        } catch(e: Exception) { emit(UnknownError<AuthUser?>(e.toString())) }
    }

    override suspend fun getUserRepos(user: String): Flow<RepositoryResponse<List<Repository>?>> = flow {
        if (user != "") {
            try {
                val networkResponse = networkRepository.getUserRepos(user)

                if (networkResponse.isSuccessful) emit(Ok<List<Repository>?>(networkResponse.body()))
                else emit(UnknownError<List<Repository>?>(networkResponse.toString()))
            } catch(e: UnknownHostException) { emit(OfflineError<List<Repository>?>())
            } catch(e: Exception) { emit(UnknownError<List<Repository>?>(e.toString())) }
        }
    }

    override suspend fun getOrgs(user: String): Flow<RepositoryResponse<List<Organisation>?>> = flow {
        if (user != "") {
            try {
                val networkResponse = networkRepository.getUserOrgs(user)

                if (networkResponse.isSuccessful) emit(Ok<List<Organisation>?>(networkResponse.body()))
                else emit(UnknownError<List<Organisation>?>(networkResponse.toString()))
            } catch(e: UnknownHostException) { emit(OfflineError<List<Organisation>?>())
            } catch(e: Exception) { emit(UnknownError<List<Organisation>?>(e.toString())) }
        }
    }

    override suspend fun getStarred(user: String): Flow<RepositoryResponse<List<Repository>?>> = flow {
        if (user != "") {
            try {
                val networkResponse = networkRepository.getUserStarred(user)

                if (networkResponse.isSuccessful) emit(Ok<List<Repository>?>(networkResponse.body()))
                else emit(UnknownError<List<Repository>?>(networkResponse.toString()))
            } catch(e: UnknownHostException) { emit(OfflineError<List<Repository>?>())
            } catch(e: Exception) { emit(UnknownError<List<Repository>?>(e.toString())) }
        }
    }
}