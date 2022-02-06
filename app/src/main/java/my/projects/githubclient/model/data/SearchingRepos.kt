package my.projects.githubclient.model.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class SearchingRepos(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<Repository>
)
