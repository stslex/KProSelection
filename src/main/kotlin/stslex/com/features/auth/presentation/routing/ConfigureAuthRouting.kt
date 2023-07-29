package stslex.com.features.auth.presentation.routing

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import stslex.com.features.auth.domain.interactor.AuthInteractor
import stslex.com.features.auth.domain.model.UserAuthModel
import stslex.com.features.auth.utils.token.JwtUnAuthConfig
import stslex.com.routing.RoutingExt

fun Application.installAuthRouting() {
    val interactor by inject<AuthInteractor>()

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
                apiKey = call.request.header(JwtUnAuthConfig.API_KEY_HEADER),
                deviceId = call.request.header(JwtUnAuthConfig.DEVICE_ID_HEADER),
                uuid = call.request.header("uuid"),
                getUser = interactor::getUserTokenModel
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