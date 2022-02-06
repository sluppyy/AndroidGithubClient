package my.projects.githubclient.model.data.structures

sealed interface GithubFile {
    val name: String
}

class Files(
    override val name: String,
    val files: Iterable<String>
    ): GithubFile

class Directories(
    override val name: String,
    val directories: Iterable<Directories>,
    val files: Iterable<Files>
    ): GithubFile