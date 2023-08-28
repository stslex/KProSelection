package stslex.com.features.auth.presentation.model.request

import io.ktor.server.auth.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAuthRequest(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
) : Principal

