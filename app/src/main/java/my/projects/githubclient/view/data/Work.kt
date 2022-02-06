package my.projects.githubclient.view.data

import android.util.Log
import my.projects.githubclient.utils.FromString
import my.projects.githubclient.utils.SelectableObject
import my.projects.githubclient.utils.Selected
import my.projects.githubclient.utils.Unselected


enum class Work(var toString: String) {
    ISSUES("Issues"),
    PULL_REQUESTS("Pull Requests"),
    DISCUSSIONS("Discussions"),
    REPOSITORIES("Repositories"),
    ORGANIZATIONS("Organizations"),
    STARRED("Starred"),
    SETTINGS("Settings");

    override fun toString() = this.toString
    companion object: FromString<Work> {
        override fun fromString(str: String): Work? {
            values().forEach { work ->
                if (str.lowercase() == work.toString.lowercase()) return work
            }
            return null
        }
    }
}

@Deprecated("use the fromString method of Work class")
fun workfromString(name:String): Work? {
    Work.values().forEach { work ->
        if (name.lowercase() == work.toString.lowercase()) return work
    }
    return null
}

fun readSelectedObject(str: String): SelectableObject<Work?>?
=   if (str.isEmpty()) null
    else when (str[0]) {
        '1' -> {
            Log.e("converting", "converted in selected from $str fst = ${str[0]}")
            Selected(Work.fromString(str.drop(1)))
        }
        '0' -> {
            Log.e("converting", "converted in unselected from $str fst = ${str[0]}")
            Unselected(Work.fromString(str.drop(1)))
        }
        else -> {
            Log.e("converting", "converted in null from $str")

            null
        }
    }