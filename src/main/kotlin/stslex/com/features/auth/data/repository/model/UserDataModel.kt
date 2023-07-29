package stslex.com.features.auth.data.repository.model

import kotlinx.serialization.SerialName

data class UserDataModel(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("password")
    val password: String,
    @SerialName("username")
    val username: String,
    @SerialName("nickname")
    val nickname: String,
)