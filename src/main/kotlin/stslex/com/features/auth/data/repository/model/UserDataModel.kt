package stslex.com.features.auth.data.repository.model

import kotlinx.serialization.SerialName

data class UserDataModel(
    @SerialName("uuid")
    val uuid: Int,
    @SerialName("password")
    val password: String,
    @SerialName("username")
    val username: String,
    @SerialName("nickname")
    val nickname: String,
)