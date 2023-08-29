package stslex.com.features.auth.presentation.token

import com.auth0.jwt.JWT
import stslex.com.features.auth.presentation.configure.AuthConfig
import java.util.*

class TokenGeneratorImpl : TokenGenerator {

    companion object {
        private const val JWT_SUBJECT = "Authentication"
        const val PAYLOAD_UUID = "payload_uuid"
        const val PAYLOAD_USERNAME = "payload_username"
        const val PAYLOAD_PASSWORD = "payload_password"
    }

    private val expiration: Date
        get() = Date(System.currentTimeMillis() + AuthConfig.VALIDITY_IN_MS)

    override fun generateToken(
        user: UserTokenModel
    ): String = JWT.create()
        .withSubject(JWT_SUBJECT)
        .withIssuer(AuthConfig.ISSUER)
        .withJWTId(user.uuid)
        .withClaim(PAYLOAD_UUID, user.uuid)
        .withClaim(PAYLOAD_USERNAME, user.username)
        .withClaim(PAYLOAD_PASSWORD, user.password)
        .withExpiresAt(expiration)
        .sign(AuthConfig.algorithm)
}