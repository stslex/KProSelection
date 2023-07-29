package stslex.com.routing.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoutingUserResponse(
    @SerialName("uuid")
    val uuid: Int,
    @SerialName("username")
    val username: String,
    @SerialName("nickname")
    val nickname: String,
) {

    companion object {

        val DEFAULT = RoutingUserResponse(
            uuid = -1,
            username = "",
            nickname = ""
        )
    }
}