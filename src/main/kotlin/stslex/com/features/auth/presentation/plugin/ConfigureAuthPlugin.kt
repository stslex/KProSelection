package stslex.com.features.auth.presentation.plugin

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import stslex.com.features.auth.domain.model.TokenUnAuthModel
import stslex.com.features.auth.presentation.routing.processApiDeviceValidation
import stslex.com.features.auth.utils.token.JwtConfig
import stslex.com.features.auth.utils.token.JwtUnAuthConfig

fun Application.installAuthPlugin() {
    install(Authentication) {
        jwt(AuthConfig.JWT_TOKEN_AUTH.configName) {
            verifier(JwtConfig.verifierAuth)
            realm = AuthConfig.JWT_TOKEN_AUTH.realm
            validate {
                // TODO verify token with uuid
                processApiDeviceValidation(
                    apiKey = request.header(JwtUnAuthConfig.API_KEY_HEADER),
                    deviceId = request.header(JwtUnAuthConfig.DEVICE_ID_HEADER)
                ) { apiKey, deviceId ->
                    TokenUnAuthModel(
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
                    TokenUnAuthModel(
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