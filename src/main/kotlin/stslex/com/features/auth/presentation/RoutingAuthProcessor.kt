package stslex.com.features.auth.presentation

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*
import stslex.com.features.auth.domain.model.UserAuthModel
import stslex.com.features.auth.domain.result.AuthResult
import stslex.com.features.auth.domain.result.RegisterResult
import stslex.com.features.auth.presentation.model.RoutingUserResponse
import stslex.com.features.auth.presentation.model.TokenResponse
import stslex.com.features.auth.utils.JwtConfig
import stslex.com.features.auth.utils.JwtUnAuthConfig

suspend fun PipelineContext<Unit, ApplicationCall>.processRegister(
    result: RegisterResult
) {
    when (result) {
        is RegisterResult.Success -> {
            call.respond(HttpStatusCode.Created, result.data)
        }

        is RegisterResult.SaveUserError -> {
            call.respond(HttpStatusCode.InternalServerError)
        }

        is RegisterResult.InvalidPassword -> {
            call.respond(HttpStatusCode.LengthRequired, RoutingUserResponse.DEFAULT)
        }

        is RegisterResult.UserIsExist -> {
            call.respond(HttpStatusCode.Conflict, RoutingUserResponse.DEFAULT)
        }
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.processAuth(
    result: AuthResult
) {
    when (result) {
        is AuthResult.Success -> {
            call.respond(HttpStatusCode.Accepted, result.data)
        }

        is AuthResult.InvalidPassword -> {
            call.respond(HttpStatusCode.PreconditionFailed, RoutingUserResponse.DEFAULT)
        }

        is AuthResult.UserIsNotExist -> {
            call.respond(HttpStatusCode.NotAcceptable, RoutingUserResponse.DEFAULT)
        }
    }
}

suspend inline fun PipelineContext<Unit, ApplicationCall>.processTokenGenerate(
    apiKey: String?,
    deviceId: String?,
    uuid: String?,
    crossinline getUser: suspend (uuid: String) -> UserAuthModel?
) {
    if (uuid == null) {
        processUnAuthToken(
            apiKey = apiKey,
            deviceId = deviceId
        )
    } else {
        val user = getUser(uuid)
        if (user != null) {
            processAuthToken(
                apiKey = apiKey,
                deviceId = deviceId,
                user = user
            )
        } else {
            processUnAuthToken(
                apiKey = apiKey,
                deviceId = deviceId
            )
        }
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.processUnAuthToken(
    apiKey: String?,
    deviceId: String?
) {
    call.processApiDeviceValidationResponse(
        apiKey = apiKey,
        deviceId = deviceId
    ) { key, id ->
        val token = JwtUnAuthConfig.generateToken(
            apiKey = key,
            deviceId = id
        )
        val response = TokenResponse(token)
        call.respond(response)
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.processAuthToken(
    apiKey: String?,
    deviceId: String?,
    user: UserAuthModel
) {
    call.processApiDeviceValidationResponse(
        apiKey = apiKey,
        deviceId = deviceId
    ) { _, _ ->
        val token = JwtConfig.generateToken(user)
        val response = TokenResponse(token)
        call.respond(response)
    }
}

suspend inline fun ApplicationCall.processApiDeviceValidationResponse(
    apiKey: String?,
    deviceId: String?,
    success: (apiKey: String, deviceId: String) -> Unit
) {
    if (apiKey.isNullOrBlank() || JwtUnAuthConfig.validateKey(apiKey).not()) {
        respond(HttpStatusCode.Unauthorized, "API KEY is invalid")
        return
    }
    if (deviceId.isNullOrBlank()) {
        respond(HttpStatusCode.Unauthorized, "DEVICE ID is invalid")
        return
    }
    success(apiKey, deviceId)
}

suspend inline fun ApplicationCall.processApiDeviceValidation(
    apiKey: String?,
    deviceId: String?,
    success: (apiKey: String, deviceId: String) -> Principal?
): Principal? {
    if (apiKey.isNullOrBlank() || JwtUnAuthConfig.validateKey(apiKey).not()) {
        respond(HttpStatusCode.Unauthorized, "API KEY is invalid")
        return null
    }
    if (deviceId.isNullOrBlank()) {
        respond(HttpStatusCode.Unauthorized, "DEVICE ID is invalid")
        return null
    }
    return success(apiKey, deviceId)
}
