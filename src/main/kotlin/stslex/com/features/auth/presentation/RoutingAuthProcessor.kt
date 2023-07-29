package stslex.com.features.auth.presentation

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*
import stslex.com.features.auth.domain.result.AuthResult
import stslex.com.features.auth.domain.result.RegisterResult
import stslex.com.features.auth.presentation.model.RoutingUserResponse
import stslex.com.features.auth.presentation.model.TokenResponse
import stslex.com.features.auth.utils.ApiConfig

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

suspend fun PipelineContext<Unit, ApplicationCall>.processUnAuthToken(
    apiKey: String?,
    deviceId: String?
) {
    call.processApiDeviceValidationResponse(
        apiKey = apiKey,
        deviceId = deviceId
    ) { key, id ->
        val token = ApiConfig.generateToken(
            apiKey = key,
            deviceId = id
        )
        val response = TokenResponse(token)
        call.respond(response)
    }
}

suspend inline fun ApplicationCall.processApiDeviceValidationResponse(
    apiKey: String?,
    deviceId: String?,
    success: (apiKey: String, deviceId: String) -> Unit
) {
    if (apiKey.isNullOrBlank() || ApiConfig.validateKey(apiKey).not()) {
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
    if (apiKey.isNullOrBlank() || ApiConfig.validateKey(apiKey).not()) {
        respond(HttpStatusCode.Unauthorized, "API KEY is invalid")
        return null
    }
    if (deviceId.isNullOrBlank()) {
        respond(HttpStatusCode.Unauthorized, "DEVICE ID is invalid")
        return null
    }
    return success(apiKey, deviceId)
}

