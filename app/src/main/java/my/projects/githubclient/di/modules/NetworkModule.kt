package my.projects.githubclient.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import my.projects.githubclient.config.GITHUB_URL
import my.projects.githubclient.model.data.AccessToken
import my.projects.githubclient.model.respository.ConfigRepository
import my.projects.githubclient.model.respository.network.NetworkGithubApi
import my.projects.githubclient.model.respository.network.retrofit.RetrofitGithubRepository
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun getNetworkGithubApi(
        okHttpClient: OkHttpClient
    ): NetworkGithubApi = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(GITHUB_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(RetrofitGithubRepository::class.java)


    @Provides
    fun getHttpClient(
        configRepository: ConfigRepository
    ): OkHttpClient {
        var okHttpClient: OkHttpClient? = null

        runBlocking {
            val (user, token) = configRepository.getAccessToken() ?: AccessToken("", "")

            okHttpClient = OkHttpClient.Builder().authenticator { _, response ->
                val credentials = Credentials.basic(user, token)

                if (credentials.equals(response.request().header("Authorization"))) null
                else response
                    .request()
                    .newBuilder()
                    .header("Authorization", credentials)
                    .build()
            }.build()
        }

        return okHttpClient!!
    }
}