package my.projects.githubclient.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class Organisation(
    @Json(name = "login")       val login               : String,
    @Json(name = "id")          val id                  : String,
    @Json(name = "node_id")     val node_id             : String,
    @Json(name = "description") val description         : String
)
