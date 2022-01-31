package my.projects.githubclient.model.data

enum class Work(val toString: String) {
    ISSUES("Issues"),
    PULL_REQUESTS("Pull Requests"),
    DISCUSSIONS("Discussions"),
    REPOSITORIES("Repositories"),
    ORGANIZATIONS("Organizations"),
    STARRED("Starred")
}