package stslex.com.features.auth.presentation.presenter.model

import io.ktor.http.*
import stslex.com.model.ApiError
import stslex.com.model.ApiError.Authentication.InvalidPasswordFormat
import stslex.com.model.ApiError.Authentication.SaveUser
import stslex.com.model.ApiErrorRespond
import stslex.com.model.MessageResponse

sealed class RegisterResult<out T>(
    val statusCode: HttpStatusCode,
    open val data: T
) {

    data object Success : RegisterResult<MessageResponse>(
        statusCode = HttpStatusCode.OK,
        data = MessageResponse("success register")
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
