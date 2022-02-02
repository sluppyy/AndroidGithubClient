package my.projects.githubclient.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import my.projects.githubclient.model.data.Organisation

@JsonClass(generateAdapter = false)
data class Repository(
    @Json(name = "id")                  val id                : Int,
    @Json(name = "node_id")             val node_id           : String,
    @Json(name = "name")                val name              : String,
    @Json(name = "full_name")           val full_name         : String,
    @Json(name = "private")             val private           : Boolean,
    @Json(name = "owner")               val owner             : Owner,
    @Json(name = "description")         val description       : String?,
    @Json(name = "fork")                val fork              : Boolean,
    @Json(name = "created_at")          val created_at        : String,
    @Json(name = "updated_at")          val updated_at        : String,
    @Json(name = "pushed_at")           val pushed_at         : String,
    @Json(name = "homepage")            val homepage          : String?,
    @Json(name = "size")                val size              : Int,
    @Json(name = "stargazers_count")    val stargazers_count  : Int,
    @Json(name = "watchers_count")      val watchers_count    : Int,
    @Json(name = "language")            val language          : String?,
    @Json(name = "has_issues")          val has_issues        : Boolean,
    @Json(name = "has_projects")        val has_projects      : Boolean,
    @Json(name = "has_downloads")       val has_downloads     : Boolean,
    @Json(name = "has_wiki")            val has_wiki          : Boolean,
    @Json(name = "has_pages")           val has_pages         : Boolean,
    @Json(name = "forks_count")         val forks_count       : Int,
    @Json(name = "mirror_url")          val mirror_url        : String?,
    @Json(name = "archived")            val archived          : Boolean,
    @Json(name = "disabled")            val disabled          : Boolean,
    @Json(name = "open_issues_count")   val open_issues_count : Int,
    @Json(name = "license")             val license           : License?,
    @Json(name = "allow_forking")       val allow_forking     : Boolean,
    @Json(name = "is_template")         val is_template       : Boolean,
    @Json(name = "topics")              val topics            : List<String>,
    @Json(name = "visibility")          val visibility        : String,
    @Json(name = "forks")               val forks             : Int,
    @Json(name = "open_issues")         val open_issues       : Int,
    @Json(name = "watchers")            val watchers          : Int,
    @Json(name = "default_branch")      val default_branch    : String,
    @Json(name = "temp_clone_token")    val temp_clone_token  : String?,
    @Json(name = "network_count")       val network_count     : Int?,
    @Json(name = "subscribers_count")   val subscribers_count : Int?
)