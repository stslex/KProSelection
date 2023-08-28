package stslex.com.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import org.koin.ktor.ext.inject
import stslex.com.features.auth.domain.interactor.AuthInteractor
import stslex.com.features.auth.presentation.plugin.AuthConfig
import stslex.com.features.auth.presentation.utils.token.JwtConfig
import stslex.com.features.auth.presentation.utils.token.JwtConfig.API_KEY_HEADER
import stslex.com.model.ApiError
import stslex.com.model.respondError

fun Application.configureAuthentication() {

    val interactor by inject<AuthInteractor>()

    install(Authentication) {
        jwt(AuthConfig.JWT_TOKEN_AUTH.configName) {
            verifier(JwtConfig.verifierAuth)
            realm = AuthConfig.JWT_TOKEN_AUTH.realm
            validate { credential ->
                val uuid = credential.jwtId ?: return@validate null
                if (JwtConfig.validateKey(request.header(API_KEY_HEADER)).not()) {
                    respondError(ApiError.Unauthorized.ApiKey)
                }
                if (
                    interactor.getUserByUuid(uuid) == null ||
                    credential.payload.getClaim("username").isMissing ||
                    credential.payload.getClaim("password").isMissing
                ) {
                    return@validate null
                }
                JWTPrincipal(credential.payload)
            }
            challenge { _, _ ->
                call.respondError(ApiError.Unauthorized.Token)
            }
        }

        basic(AuthConfig.DEFAULT.configName) {
            realm = AuthConfig.DEFAULT.realm
            validate { credential ->
                if (JwtConfig.validateKey(request.header(API_KEY_HEADER)).not()) {
                    respondError(ApiError.Unauthorized.ApiKey)
                    null
                } else {
                    UserIdPrincipal(credential.name)
                }
            }
        }
    }
}
