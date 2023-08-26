package stslex.com.features.auth.presentation

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.util.pipeline.*
import stslex.com.features.auth.presentation.model.respond.TokenRespond
import stslex.com.features.auth.presentation.utils.token.AuthCache
import stslex.com.features.auth.presentation.utils.token.JwtConfig
import stslex.com.features.auth.presentation.utils.token.JwtUnAuthConfig
import stslex.com.features.auth.presentation.utils.token.model.UserTokenModel
import stslex.com.model.ApiError.Unauthorized.ApiKey
import stslex.com.model.ApiError.Unauthorized.DeviceId

suspend inline fun PipelineContext<Unit, ApplicationCall>.processTokenGenerate(
    apiKey: String?,
    deviceId: String?,
    tokenModel: UserTokenModel?,
) {
    if (tokenModel == null) {
        processUnAuthToken(
            apiKey = apiKey,
            deviceId = deviceId
        )
    } else {
        processAuthToken(
            apiKey = apiKey,
            deviceId = deviceId,
            user = tokenModel
        )
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
        val response = TokenRespond(token)
        call.respond(response)
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.processAuthToken(
    apiKey: String?,
    deviceId: String?,
    user: UserTokenModel
) {
    call.processApiDeviceValidationResponse(
        apiKey = apiKey,
        deviceId = deviceId
    ) { _, _ ->
        val token = AuthCache.getToken(user.uuid)
            ?: JwtConfig.generateToken(user)
        val response = TokenRespond(token)
        call.respond(response)
    }
}

suspend inline fun ApplicationCall.processApiDeviceValidationResponse(
    apiKey: String?,
    deviceId: String?,
    success: (apiKey: String, deviceId: String) -> Unit
) {
    if (apiKey.isNullOrBlank() || JwtUnAuthConfig.validateKey(apiKey).not()) {
        respond(ApiKey.statusCode, ApiKey.data)
        return
    }
    if (deviceId.isNullOrBlank()) {
        respond(DeviceId.statusCode, DeviceId.data)
        return
    }
    success(apiKey, deviceId)
}
