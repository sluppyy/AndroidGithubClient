package my.projects.githubclient.model.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class GithubFile(
    val name: String,
    val path: String,
    val type: String
)

