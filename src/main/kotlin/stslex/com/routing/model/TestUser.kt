package stslex.com.routing.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TestUser(
    @SerialName("username")
    val username: String
)