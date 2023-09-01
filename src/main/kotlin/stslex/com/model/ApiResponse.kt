package stslex.com.model

sealed interface ApiResponse<out T> {

    data class Success<out T>(
        val data: T
    ) : ApiResponse<T>

    data class Error<out T>(
        val apiError: ApiError
    ) : ApiResponse<T>

}

suspend inline fun <T> ApiResponse<T>.onSuccess(
    crossinline action: suspend (T) -> Unit
): ApiResponse<T> = apply {
    if (this is ApiResponse.Success<T>) {
        action(data)
    }
}

suspend inline fun <T> ApiResponse<T>.onError(
    crossinline action: suspend (ApiError) -> Unit
): ApiResponse<T> = apply {
    if (this is ApiResponse.Error<T>) {
        action(apiError)
    }
}
