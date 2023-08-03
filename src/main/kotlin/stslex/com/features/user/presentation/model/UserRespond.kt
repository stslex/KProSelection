package stslex.com.features.user.presentation.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRespond(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("username")
    val username: String,
    @SerialName("nickname")
    val nickname: String
)