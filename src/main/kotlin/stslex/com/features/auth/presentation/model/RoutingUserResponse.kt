package stslex.com.features.auth.presentation.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutingUserResponse(
    @SerialName("uuid")
    val uuid: String,
    @SerialName("username")
    val username: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("token")
    val token: String
) {

    companion object {

        val DEFAULT = RoutingUserResponse(
            uuid = "",
            username = "",
            nickname = "",
            token = ""
        )
    }
}