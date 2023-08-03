package stslex.com.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagingResponse(
    @SerialName("page_size")
    val pageSize: Int,
    @SerialName("page_number")
    val pageNumber: Int
)
