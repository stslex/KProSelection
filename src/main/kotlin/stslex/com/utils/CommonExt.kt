package stslex.com.utils

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.util.pipeline.*
import stslex.com.features.auth.presentation.token.TokenGeneratorImpl

val PipelineContext<Unit, ApplicationCall>.payload_uuid: String?
    get() = call.authentication.principal<JWTPrincipal>()
        ?.payload
        ?.getClaim(TokenGeneratorImpl.PAYLOAD_UUID)
        ?.asString()