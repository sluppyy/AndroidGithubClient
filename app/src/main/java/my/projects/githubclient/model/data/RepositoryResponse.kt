package my.projects.githubclient.model.data

sealed interface RepositoryResponse<T>

class Ok<T>(val body: T) : RepositoryResponse<T>
class OfflineError<T> : RepositoryResponse<T>
class AuthError<T>: RepositoryResponse<T>
class UnknownError<T>(val error: String) : RepositoryResponse<T>