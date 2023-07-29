package stslex.com.features.auth.domain.model

import io.ktor.server.auth.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserAuthModel(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
) : Principal

