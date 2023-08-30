package stslex.com.model

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

sealed class ApiError(
    val statusCode: HttpStatusCode,
    val messageCode: Int,
    val message: String,
) {

    val data = ApiErrorRespond(
        messageCode = messageCode,
        message = message
    )

    sealed class Unauthorized(
        messageCode: Int,
        message: String,
    ) : ApiError(
        statusCode = HttpStatusCode.Unauthorized,
        messageCode = messageCode,
        message = message
    ) {

        data object Token : Unauthorized(
            messageCode = 1,
            message = "Token is Invalid or has been expired"
        )

        data object ApiKey : Unauthorized(
            messageCode = 2,
            message = "Api Key is Invalid"
        )

        data object DeviceId : Unauthorized(
            messageCode = 3,
            message = "Device Id is Invalid"
        )
    }

    sealed class Authentication(
        statusCode: HttpStatusCode,
        messageCode: Int,
        message: String,
    ) : ApiError(
        statusCode = statusCode,
        messageCode = messageCode,
        message = message
    ) {

        data object SaveUser : Authentication(
            statusCode = HttpStatusCode.InternalServerError,
            messageCode = 100,
            message = "Error while saving user, try again later"
        )

        data object InvalidPassword : Authentication(
            statusCode = HttpStatusCode.PreconditionFailed,
            messageCode = 101,
            message = "Password is not valid"
        )

        data object InvalidPasswordFormat : Authentication(
            statusCode = HttpStatusCode.LengthRequired,
            messageCode = 102,
            message = "Password not in right format"
        )

        data object UserIsExist : Authentication(
            statusCode = HttpStatusCode.Conflict,
            messageCode = 103,
            message = "User with this username is already exist"
        )

        data object UserIsNotExist : Authentication(
            statusCode = HttpStatusCode.NotAcceptable,
            messageCode = 103,
            message = "Couldn't find user with this username"
        )

        data object InvalidUsername : Authentication(
            statusCode = HttpStatusCode.LengthRequired,
            messageCode = 104,
            message = "Username is invalid"
        )
    }
}

suspend fun ApplicationCall.respondError(error: ApiError) {
    respond(error.statusCode, error.data)
}
