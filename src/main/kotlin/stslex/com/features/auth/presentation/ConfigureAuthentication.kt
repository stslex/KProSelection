package stslex.com.features.auth.presentation

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import stslex.com.features.auth.domain.interactor.AuthInteractor
import stslex.com.features.auth.domain.model.TokenUnAuthModel
import stslex.com.features.auth.domain.model.UserAuthModel
import stslex.com.features.auth.presentation.model.TokenResponse
import stslex.com.features.auth.utils.JwtConfig
import stslex.com.features.auth.utils.JwtUnAuthConfig.API_KEY_HEADER
import stslex.com.features.auth.utils.JwtUnAuthConfig.DEVICE_ID_HEADER
import stslex.com.features.auth.utils.JwtUnAuthConfig.verifierUnAuth
import stslex.com.routing.RoutingExt

fun Application.configureAuthentication() {
    val interactor by inject<AuthInteractor>()

    install(Authentication) {
        jwt("jwt.token.auth") {
            verifier(JwtConfig.verifierAuth)
            realm = "com.stslex"
            validate {
                processApiDeviceValidation(
                    apiKey = request.header(API_KEY_HEADER),
                    deviceId = request.header(DEVICE_ID_HEADER)
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
        jwt("jwt.token") {
            realm = "Access to the '/' path"
            verifier(verifierUnAuth)
            validate {
                processApiDeviceValidation(
                    apiKey = request.header(API_KEY_HEADER),
                    deviceId = request.header(DEVICE_ID_HEADER)
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


    routing {
        authenticate(
            "jwt.token",
            "jwt.token.auth"
        ) {
            post("${RoutingExt.API_HOST}/passport/register") {
                val user = call.receive<UserAuthModel>()
                val result = interactor.register(user)
                processRegister(result)
            }

            post("${RoutingExt.API_HOST}/passport/auth") {
                val user = call.receive<UserAuthModel>()
                val result = interactor.auth(user)
                processAuth(result)
            }
        }

        get("${RoutingExt.API_HOST}/token") {
            processTokenGenerate(
                apiKey = call.request.header(API_KEY_HEADER),
                deviceId = call.request.header(DEVICE_ID_HEADER),
                uuid = call.request.header("uuid"),
                getUser = interactor::getUser
            )
        }

        authenticate("jwt.token") {
            get("${RoutingExt.API_HOST}/testToken") {
                call.respondText("response")
            }
        }

        authenticate("jwt.token.auth") {
            get("${RoutingExt.API_HOST}/user/list") {
                val allUsers = interactor.getAll()
                call.respond(allUsers)
            }
        }
    }
}
