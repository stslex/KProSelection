package stslex.com.features.auth.presentation.utils.token.model

data class UserTokenModel(
    val uuid: String,
    val username: String,
    val password: String
)