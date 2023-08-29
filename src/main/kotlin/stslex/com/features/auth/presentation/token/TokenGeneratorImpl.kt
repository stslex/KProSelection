package stslex.com.features.auth.presentation.token

import com.auth0.jwt.JWT
import stslex.com.features.auth.presentation.configure.AuthConfig
import java.util.*

class TokenGeneratorImpl : TokenGenerator {

    private val expiration: Date
        get() = Date(System.currentTimeMillis() + AuthConfig.VALIDITY_IN_MS)

    override fun generateToken(
        user: UserTokenModel
    ): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(AuthConfig.ISSUER)
        .withJWTId(user.uuid)
        .withClaim("username", user.username)
        .withClaim("password", user.password)
        .withExpiresAt(expiration)
        .sign(AuthConfig.algorithm)
}