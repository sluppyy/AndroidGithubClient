package my.projects.githubclient.model.respository.local.room.dao

import androidx.room.*
import my.projects.githubclient.model.data.AuthUser

@Dao
interface AuthUserDao {
    @Query("SELECT * FROM 'auth_users'")
    suspend fun getAuthUser(): AuthUser?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAuthUser(user: AuthUser)

    @Query("DELETE FROM 'auth_users'")
    suspend fun clear()
}