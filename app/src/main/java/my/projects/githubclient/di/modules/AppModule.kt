package my.projects.githubclient.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import my.projects.githubclient.config.GITHUB_URL
import my.projects.githubclient.model.respository.network.NetworkGithubApi
import my.projects.githubclient.model.respository.network.retrofit.RetrofitGithubRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getNetworkGithubApi(): NetworkGithubApi {


        return Retrofit.Builder()
            .baseUrl(GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitGithubRepository::class.java)
    }
}