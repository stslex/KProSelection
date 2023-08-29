package stslex.com.features.auth.presentation.token

data class UserTokenModel(
    val uuid: String,
    val username: String,
    val password: String
)