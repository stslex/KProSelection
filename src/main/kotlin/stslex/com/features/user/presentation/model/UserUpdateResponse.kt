package stslex.com.features.user.presentation.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateResponse(
    @SerialName("nickname")
    val nickname: String
)
