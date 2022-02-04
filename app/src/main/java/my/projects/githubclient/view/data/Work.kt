package my.projects.githubclient.view.data

enum class Work(val toString: String) {
    ISSUES("Issues"),
    PULL_REQUESTS("Pull Requests"),
    DISCUSSIONS("Discussions"),
    REPOSITORIES("Repositories"),
    ORGANIZATIONS("Organizations"),
    STARRED("Starred"),
    SETTINGS("Settings")
}

fun workfromString(name:String): Work? {
    Work.values().forEach { work ->
        if (name.lowercase() == work.toString.lowercase())
        return work
    }
    return null
}