package stslex.com.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import stslex.com.features.auth.presentation.model.response.TokenUnAuthResponse
import stslex.com.features.auth.presentation.plugin.AuthConfig
import stslex.com.features.auth.presentation.processApiDeviceValidation
import stslex.com.features.auth.presentation.utils.token.AuthCache.isValid
import stslex.com.features.auth.presentation.utils.token.JwtConfig
import stslex.com.features.auth.presentation.utils.token.JwtUnAuthConfig

fun Application.configureAuthentication() {

    install(Authentication) {
        jwt(AuthConfig.JWT_TOKEN_AUTH.configName) {
            verifier(JwtConfig.verifierAuth)
            realm = AuthConfig.JWT_TOKEN_AUTH.realm
            validate { credential ->
                if (isTokenValid(credential).not()) {
                    return@validate null
                }
                processApiDeviceValidation(
                    apiKey = request.header(JwtUnAuthConfig.API_KEY_HEADER),
                    deviceId = request.header(JwtUnAuthConfig.DEVICE_ID_HEADER)
                ) { apiKey, deviceId ->
                    TokenUnAuthResponse(
                        apiKey = apiKey,
                        deviceId = deviceId
                    )
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
        jwt(AuthConfig.JWT_TOKEN.configName) {
            realm = AuthConfig.JWT_TOKEN.realm
            verifier(JwtUnAuthConfig.verifierUnAuth)
            validate {
                processApiDeviceValidation(
                    apiKey = request.header(JwtUnAuthConfig.API_KEY_HEADER),
                    deviceId = request.header(JwtUnAuthConfig.DEVICE_ID_HEADER)
                ) { apiKey, deviceId ->
                    TokenUnAuthResponse(
                        apiKey = apiKey,
                        deviceId = deviceId
                    )
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}

private val ApplicationCall.isTokenValid: (credential: JWTCredential) -> Boolean
    get() = { credential ->
        isValid(
            jwtId = credential.jwtId,
            uuid = request.header(JwtUnAuthConfig.UUID_HEADER)
        )
    }