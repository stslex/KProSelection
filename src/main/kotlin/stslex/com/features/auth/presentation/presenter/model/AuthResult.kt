package stslex.com.features.auth.presentation.presenter.model

import io.ktor.http.*
import stslex.com.features.auth.presentation.model.respond.AuthUserResponse
import stslex.com.model.ApiError
import stslex.com.model.ApiErrorRespond

sealed class AuthResult<out T>(
    val statusCode: HttpStatusCode,
    open val data: T
) {

    data class Success(
        override val data: AuthUserResponse
    ) : AuthResult<AuthUserResponse>(
        statusCode = HttpStatusCode.OK,
        data = data
    )

    sealed class Error(error: ApiError) : AuthResult<ApiErrorRespond>(
        statusCode = error.statusCode,
        data = error.data
    ) {

        data object InvalidPassword : Error(ApiError.Authentication.InvalidPassword)

        data object UserIsNotExist : Error(ApiError.Authentication.UserIsNotExist)
    }
}