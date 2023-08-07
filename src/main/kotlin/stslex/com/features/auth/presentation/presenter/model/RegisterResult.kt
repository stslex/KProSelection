package stslex.com.features.auth.presentation.presenter.model

import io.ktor.http.*
import stslex.com.features.auth.presentation.model.respond.AuthUserRespond
import stslex.com.model.ApiError
import stslex.com.model.ApiError.Authentication.InvalidPasswordFormat
import stslex.com.model.ApiError.Authentication.SaveUser
import stslex.com.model.ApiErrorRespond

sealed class RegisterResult<out T>(
    val statusCode: HttpStatusCode,
    open val data: T
) {

    data class Success(
        override val data: AuthUserRespond
    ) : RegisterResult<AuthUserRespond>(
        statusCode = HttpStatusCode.OK,
        data = data
    )

    sealed class Error(
        error: ApiError
    ) : RegisterResult<ApiErrorRespond>(
        statusCode = error.statusCode,
        data = error.data
    ) {

        data object InvalidPassword : Error(InvalidPasswordFormat)

        data object UserIsExist : Error(ApiError.Authentication.UserIsExist)

        data object SaveUserError : Error(SaveUser)
    }
}

