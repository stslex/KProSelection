package stslex.com.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    @SerialName("message_code")
    val messageCode: Int,
    @SerialName("message")
    val message: String
)