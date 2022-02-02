package my.projects.githubclient.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class License(
    @Json(name = "key")     val key: String,
    @Json(name = "name")    val name: String,
    @Json(name = "spdx_id") val spdx_id: String,
    @Json(name = "url")     val url: String,
    @Json(name = "node_id") val node_id: String
)
