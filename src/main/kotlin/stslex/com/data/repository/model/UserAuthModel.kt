package stslex.com.data.repository.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAuthModel(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
)

