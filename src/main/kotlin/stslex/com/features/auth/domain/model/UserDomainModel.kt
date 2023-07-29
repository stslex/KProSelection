package stslex.com.features.auth.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDomainModel(
    @SerialName("username")
    val username: String
)
