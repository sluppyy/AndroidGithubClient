package my.projects.githubclient.utils

interface FromString<T> {
    fun fromString(str: String): T?
}