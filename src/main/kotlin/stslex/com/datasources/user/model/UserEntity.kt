package stslex.com.datasources.user.model

data class UserEntity(
    val uuid: Int,
    val username: String,
    val nickname: String,
    val password: String
)

