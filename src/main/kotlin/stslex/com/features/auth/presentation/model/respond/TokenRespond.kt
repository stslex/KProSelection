package stslex.com.features.auth.presentation.model.respond

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenRespond(
    @SerialName("token")
    val token: String
)
