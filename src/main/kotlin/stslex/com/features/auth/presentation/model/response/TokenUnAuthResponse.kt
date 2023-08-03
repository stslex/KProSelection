package stslex.com.features.auth.presentation.model.response

import io.ktor.server.auth.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenUnAuthResponse(
    @SerialName("api_key")
    val apiKey: String,
    @SerialName("device_id")
    val deviceId: String
) : Principal
