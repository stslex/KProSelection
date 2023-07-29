package stslex.com.features.auth.domain.model

import io.ktor.server.auth.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenUnAuthModel(
    @SerialName("api_key")
    val apiKey: String,
    @SerialName("device_id")
    val deviceId: String
) : Principal
