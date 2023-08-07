package stslex.com.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagingResponse(
    @SerialName("page_size")
    val pageSize: Int = 10,
    @SerialName("page_number")
    val pageNumber: Int = 0
)
