package my.projects.githubclient.model.respository.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import my.projects.githubclient.model.data.Repository

@Dao
interface ReposDao {
    @Query("SELECT * FROM 'repos'")
    suspend fun getAllRepos(): List<Repository>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<Repository>)

    @Query("DELETE FROM 'repos'")
    suspend fun clear()
}