package my.projects.githubclient.model.respository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import my.projects.githubclient.config.GITHUB_URL
import my.projects.githubclient.model.data.*
import my.projects.githubclient.model.respository.local.LocalRepository
import my.projects.githubclient.model.respository.network.NetworkGithubApi
import my.projects.githubclient.model.respository.network.retrofit.RetrofitGithubRepository
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRepository @Inject constructor(
    private var networkRepository: NetworkGithubApi,
    private val localRepository: LocalRepository
): GithubRepository {
    private val _is_authorized = MutableStateFlow(false)
    override val isAuthorized: StateFlow<Boolean> = _is_authorized

    //TODO("получать новый клиент(или фабрику) из конструктора а не создавать его самому")
    override suspend fun updateClient() {
        val (user, token) = localRepository.getAccessToken() ?: AccessToken("", "")

        if (user == "" || token == "") {
            networkRepository = Retrofit.Builder()
                .baseUrl(GITHUB_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(RetrofitGithubRepository::class.java)
        } else {
            val okHttpClient: OkHttpClient = OkHttpClient.Builder().authenticator { _, response ->
                val credentials = Credentials.basic(user, token)

                if (credentials.equals(response.request().header("Authorization"))) null
                else response
                    .request()
                    .newBuilder()
                    .header("Authorization", credentials)
                    .build()
            }.build()

            networkRepository = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(GITHUB_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(RetrofitGithubRepository::class.java)
        }
        getAuthUser()
    }

    override suspend fun clearData() {
        _is_authorized.value = false
        localRepository.deleteAuthUser()
        localRepository.deleteRepos()
    }

    override suspend fun getUser(user: String)
    : Flow<RepositoryResponse<User?>> = flow {
        try {
            val networkResponse = networkRepository.getUser(user)

            if (networkResponse.isSuccessful) {
                emit(Ok(networkResponse.body()))
                _is_authorized.emit(true)
            }
            else {
                when(networkResponse.code()) {
                    401, 403  -> {
                        emit(AuthError<User?>())
                        _is_authorized.emit(false)
                        Log.e("auth_error", networkResponse.code().toString() + " " + networkResponse.errorBody().toString())
                    }
                    else -> emit(UnknownError<User?>(networkResponse.toString()))
                }
            }
        } catch(e: UnknownHostException) { emit(OfflineError<User?>())
        } catch(e: Exception) { emit(UnknownError<User?>(e.toString())) }
    }

    override suspend fun getAuthUser()
    : Flow<RepositoryResponse<AuthUser?>> = flow {
        try {
            val localUser = localRepository.getAuthUser()
            if (localUser != null) emit(Ok<AuthUser?>(localUser))
            val networkResponse = networkRepository.getAuthUser()

            if (networkResponse.isSuccessful and (networkResponse.body() != null)) {
                emit(Ok(networkResponse.body()))
                localRepository.updateAuthUser(networkResponse.body()!!)
                _is_authorized.emit(true)
            } else {
                when(networkResponse.code()) {
                    401, 403  -> {
                        emit(AuthError<AuthUser?>())
                        _is_authorized.emit(false)
                    }
                    else -> emit(UnknownError<AuthUser?>(networkResponse.toString()))
                }
            }
        } catch(e: UnknownHostException) { emit(OfflineError<AuthUser?>())
        } catch(e: Exception) { emit(UnknownError<AuthUser?>(e.toString())) }
    }

    override suspend fun getUserRepos(user: String)
    : Flow<RepositoryResponse<List<Repository>?>> = flow {
        if (user != "") {
            try {
                val localRepos = localRepository.getRepos()
                if (localRepos.isNotEmpty()) emit(Ok<List<Repository>?>(localRepos))
                val networkResponse = networkRepository.getUserRepos(user)

                if (networkResponse.isSuccessful) {
                    emit(Ok<List<Repository>?>(networkResponse.body()))
                    if (networkResponse.body()?.isNotEmpty() == true)
                        localRepository.updateRepos(networkResponse.body()!!)
                    _is_authorized.emit(true)
                } else {
                    when(networkResponse.code()) {
                        401, 403  -> {
                            emit(AuthError<List<Repository>?>())
                            _is_authorized.emit(false)
                        }
                        else -> emit(UnknownError<List<Repository>?>(networkResponse.toString()))
                    }
                }
            } catch(e: UnknownHostException) { emit(OfflineError<List<Repository>?>())
            } catch(e: Exception) { emit(UnknownError<List<Repository>?>(e.toString())) }
        }
    }

    override suspend fun getOrgs(user: String)
    : Flow<RepositoryResponse<List<Organisation>?>> = flow {
        if (user != "") {
            try {
                val networkResponse = networkRepository.getUserOrgs(user)

                if (networkResponse.isSuccessful) {
                    emit(Ok<List<Organisation>?>(networkResponse.body()))
                    _is_authorized.emit(true)
                }
                else {
                    when(networkResponse.code()) {
                        401, 403  -> {
                            emit(AuthError<List<Organisation>?>())
                            _is_authorized.emit(false)
                        }
                        else -> emit(UnknownError<List<Organisation>?>(networkResponse.toString()))
                    }
                }
            } catch(e: UnknownHostException) { emit(OfflineError<List<Organisation>?>())
            } catch(e: Exception) { emit(UnknownError<List<Organisation>?>(e.toString())) }
        }
    }

    override suspend fun getStarred(user: String)
    : Flow<RepositoryResponse<List<Repository>?>> = flow {
        if (user != "") {
            try {
                val networkResponse = networkRepository.getUserStarred(user)

                if (networkResponse.isSuccessful) {
                    emit(Ok<List<Repository>?>(networkResponse.body()))
                    _is_authorized.emit(true)
                }
                else {
                    when(networkResponse.code()) {
                        401, 403  -> {
                            emit(AuthError<List<Repository>?>())
                            _is_authorized.emit(false)
                        }
                        else -> emit(UnknownError<List<Repository>?>(networkResponse.toString()))
                    }
                }
            } catch(e: UnknownHostException) { emit(OfflineError<List<Repository>?>())
            } catch(e: Exception) { emit(UnknownError<List<Repository>?>(e.toString())) }
        }
    }

    override suspend fun checkAuth() {
        try {
            val networkResponse = networkRepository.checkAuth()

            if (networkResponse.isSuccessful) _is_authorized.emit(true)
            else {
                when(networkResponse.code()) {
                    401, 403  -> {
                        _is_authorized.emit(false)
                    }

                }
            }
        } catch(e: Exception) {_is_authorized.emit(false)}
    }

    override suspend fun getGithubFile(repository: Repository, path: String)
    : Flow<RepositoryResponse<List<GithubFile>?>> = flow {
        try {
            val networkResponse = networkRepository.getGithubFile(
                user = repository.owner.login,
                repository = repository.name,
                path = path
            )

            if (networkResponse.isSuccessful) {
                emit(Ok<List<GithubFile>?>(networkResponse.body()))
                _is_authorized.emit(true)
            }
            else {
                when(networkResponse.code()) {
                    401, 403  -> emit(AuthError<List<GithubFile>?>())
                    else -> emit(UnknownError<List<GithubFile>?>(networkResponse.toString()))
                }
            }
        } catch(e: UnknownHostException) { emit(OfflineError<List<GithubFile>?>())
        } catch(e: Exception) { emit(UnknownError<List<GithubFile>?>(e.toString())) }
    }

    override suspend fun searchRepos(repositoryName: String, perPage: Int, page: Int): Flow<RepositoryResponse<SearchingRepos?>> = flow {
        try {
            val networkResponse = networkRepository.searchReposByName(repositoryName, perPage = perPage, page = page)

            if (networkResponse.isSuccessful) {
                emit(Ok<SearchingRepos?>(networkResponse.body()))
                _is_authorized.emit(true)
            }
            else {
                when(networkResponse.code()) {
                    401, 403  -> emit(AuthError<SearchingRepos?>())
                    else -> emit(UnknownError<SearchingRepos?>(networkResponse.toString()))
                }
            }
        } catch(e: UnknownHostException) { emit(OfflineError<SearchingRepos?>())
        } catch(e: Exception) { emit(UnknownError<SearchingRepos?>(e.toString())) }
    }
}