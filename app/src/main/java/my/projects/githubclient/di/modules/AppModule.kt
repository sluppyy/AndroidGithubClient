package my.projects.githubclient.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.projects.githubclient.config.GITHUB_TOKEN
import my.projects.githubclient.config.GITHUB_URL
import my.projects.githubclient.config.GITHUB_USER
import my.projects.githubclient.model.respository.network.NetworkGithubApi
import my.projects.githubclient.model.respository.network.retrofit.RetrofitGithubRepository
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getNetworkGithubApi(): NetworkGithubApi {
        val okHttpClient = OkHttpClient.Builder().authenticator { _, response ->
            response.request().newBuilder().header("Authorization", Credentials.basic(GITHUB_USER, GITHUB_TOKEN)).build()
        }.build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(GITHUB_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(RetrofitGithubRepository::class.java)
    }
}