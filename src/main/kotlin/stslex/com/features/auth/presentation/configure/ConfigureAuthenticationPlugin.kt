package stslex.com.features.auth.presentation.configure

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import stslex.com.features.auth.presentation.configure.AuthConfig.API_KEY_HEADER_NAME
import stslex.com.features.auth.presentation.presenter.AuthPresenter
import stslex.com.model.ApiError
import stslex.com.model.respondError

fun Application.configureAuthenticationPlugin(
    presenter: AuthPresenter
) {
    install(Authentication) {
        jwt(AuthConfigType.JWT_TOKEN_AUTH.configName) {
            verifier(AuthConfig.verifierAuth)
            realm = AuthConfigType.JWT_TOKEN_AUTH.realm
            validate { credential ->
                JWTPrincipal(credential.payload).takeIf {
                    val uuid = credential.jwtId
                    uuid != null &&
                            AuthConfig.isKeyValid(request.header(API_KEY_HEADER_NAME)) &&
                            presenter.isUserExist(uuid)
                }
            }
            challenge { _, _ ->
                call.respondError(ApiError.Unauthorized.Token)
            }
        }

        apiKey(AuthConfigType.DEFAULT.configName) {
            headerName = API_KEY_HEADER_NAME
            validate { apiKey ->
                ApiKeyPrincipal(apiKey).takeIf {
                    AuthConfig.isKeyValid(request.header(API_KEY_HEADER_NAME))
                }
            }
            challenge {
                respondError(ApiError.Unauthorized.ApiKey)
            }
        }
    }
}