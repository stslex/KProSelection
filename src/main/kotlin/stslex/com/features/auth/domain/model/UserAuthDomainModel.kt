package stslex.com.features.auth.domain.model

data class UserAuthDomainModel(
    val uuid: String,
    val username: String,
    val nickname: String,
    val password: String
)
