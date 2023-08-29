package stslex.com.features.auth.presentation.configure

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import stslex.com.features.auth.presentation.configure.AuthConfig.API_KEY_HEADER_NAME
import stslex.com.features.auth.presentation.presenter.AuthPresenter
import stslex.com.features.auth.presentation.token.TokenGeneratorImpl.Companion.PAYLOAD_PASSWORD
import stslex.com.features.auth.presentation.token.TokenGeneratorImpl.Companion.PAYLOAD_USERNAME
import stslex.com.features.auth.presentation.token.TokenGeneratorImpl.Companion.PAYLOAD_UUID
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
                val uuid = credential.payload
                    .getClaim(PAYLOAD_UUID)
                    .asString()
                    ?: return@validate null
                val username = credential.payload
                    .getClaim(PAYLOAD_USERNAME)
                    .asString()
                    ?: return@validate null
                val password = credential.payload
                    .getClaim(PAYLOAD_PASSWORD)
                    .asString()
                    ?: return@validate null

                val isUserValid = presenter.isUserValid(
                    uuid = uuid,
                    username = username,
                    password = password
                )
                val isApiKeyValid = AuthConfig.isKeyValid(request.header(API_KEY_HEADER_NAME))
                if (isUserValid && isApiKeyValid) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
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