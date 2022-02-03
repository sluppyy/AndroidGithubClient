package my.projects.githubclient.model.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Entity(tableName = "repos")
@JsonClass(generateAdapter = false)
data class Repository(
    @Json(name = "id")  @PrimaryKey     var id                : Int             = 0,
    @Json(name = "node_id")             var node_id           : String          = "",
    @Json(name = "name")                var name              : String          = "",
    @Json(name = "full_name")           var full_name         : String          = "",
    @Json(name = "private")             var private           : Boolean         = true,
    @Json(name = "owner")               var owner             : Owner           = Owner("", 0, "", "", "", "", true),
    @Json(name = "description")         var description       : String?         = "",
    @Json(name = "fork")                var fork              : Boolean         = true,
    @Json(name = "created_at")          var created_at        : String          = "",
    @Json(name = "updated_at")          var updated_at        : String          = "",
    @Json(name = "pushed_at")           var pushed_at         : String          = "",
    @Json(name = "homepage")            var homepage          : String?         = "",
    @Json(name = "size")                var size              : Int             = 0,
    @Json(name = "stargazers_count")    var stargazers_count  : Int             = 0,
    @Json(name = "watchers_count")      var watchers_count    : Int             = 0,
    @Json(name = "language")            var language          : String?         = "",
    @Json(name = "has_issues")          var has_issues        : Boolean         = true,
    @Json(name = "has_projects")        var has_projects      : Boolean         = true,
    @Json(name = "has_downloads")       var has_downloads     : Boolean         = true,
    @Json(name = "has_wiki")            var has_wiki          : Boolean         = true,
    @Json(name = "has_pages")           var has_pages         : Boolean         = true,
    @Json(name = "forks_count")         var forks_count       : Int             = 0,
    @Json(name = "mirror_url")          var mirror_url        : String?         = "",
    @Json(name = "archived")            var archived          : Boolean         = true,
    @Json(name = "disabled")            var disabled          : Boolean         = true,
    @Json(name = "open_issues_count")   var open_issues_count : Int             = 0,
    @Json(name = "license")             var license           : License?        = License("", "", "", "", ""),
    @Json(name = "allow_forking")       var allow_forking     : Boolean         = true,
    @Json(name = "topics")              var topics            : List<String>    = emptyList(),
    @Json(name = "visibility")          var visibility        : String          = "",
    @Json(name = "forks")               var forks             : Int             = 0,
    @Json(name = "open_issues")         var open_issues       : Int             = 0,
    @Json(name = "watchers")            var watchers          : Int             = 0,
    @Json(name = "default_branch")      var default_branch    : String          = "",
    @Json(name = "temp_clone_token")    var temp_clone_token  : String?         = "",
    @Json(name = "network_count")       var network_count     : Int?            = 0,
    @Json(name = "subscribers_count")   var subscribers_count : Int?            = 0
)