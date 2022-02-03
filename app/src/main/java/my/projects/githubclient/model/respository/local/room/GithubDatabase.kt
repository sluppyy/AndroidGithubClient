package my.projects.githubclient.model.respository.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import my.projects.githubclient.model.data.AuthUser
import my.projects.githubclient.model.data.Repository
import my.projects.githubclient.model.respository.local.room.converters.LicenseConverter
import my.projects.githubclient.model.respository.local.room.converters.OwnerConverter
import my.projects.githubclient.model.respository.local.room.converters.TopicsConverter
import my.projects.githubclient.model.respository.local.room.dao.AuthUserDao
import my.projects.githubclient.model.respository.local.room.dao.ReposDao

@Database(entities = [AuthUser::class, Repository::class], version = 1)
@TypeConverters(OwnerConverter::class, LicenseConverter::class, TopicsConverter::class)
abstract class GithubDatabase: RoomDatabase() {
    abstract fun authUserDao(): AuthUserDao
    abstract fun reposDao(): ReposDao
}
