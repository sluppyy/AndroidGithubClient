package my.projects.githubclient.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "access_token")
data class AccessToken(
    @PrimaryKey
    val userLogin: String,
    val userToken: String
)