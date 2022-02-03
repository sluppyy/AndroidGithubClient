package my.projects.githubclient.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import my.projects.githubclient.model.respository.local.LocalRepository
import my.projects.githubclient.model.respository.local.room.GithubDatabase
import my.projects.githubclient.model.respository.local.room.RoomRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBmodule {
    @Provides
    @Singleton
    fun getGithubDatabase(
        @ApplicationContext appContext: Context
    ): GithubDatabase = Room
        .databaseBuilder(
            appContext,
            GithubDatabase::class.java,
            "github_db"
        ).build()

    @Provides
    @Singleton
    fun getLocalRepository(
        githubDatabase: GithubDatabase
    ): LocalRepository = RoomRepository(
        authUserDao = githubDatabase.authUserDao(),
        reposDao = githubDatabase.reposDao())
}