package my.projects.githubclient.model.respository.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import my.projects.githubclient.model.data.AccessToken

@Dao
interface AccessTokenDao {
    @Query("SELECT * FROM 'access_token'")
    suspend fun getAccessToken(): AccessToken?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccessToken(accessToken: AccessToken)

    @Query("DELETE FROM 'access_token'")
    suspend fun clear()
}