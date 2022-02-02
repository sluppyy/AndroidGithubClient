package my.projects.githubclient.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class Owner(
    @Json(name = "login")       val login               : String,
    @Json(name = "id")          val id                  : Int,
    @Json(name = "avatar_url")  val avatar_url          : String,
    @Json(name = "gravatar_id") val gravatar_id         : String,
    @Json(name = "url")         val url                 : String,
    @Json(name = "type")        val type                : String,
    @Json(name = "site_admin")  val site_admin          : Boolean
)
