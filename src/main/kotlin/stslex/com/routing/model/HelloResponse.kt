package stslex.com.routing.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HelloResponse(
    @SerialName("text")
    val hello: String
)