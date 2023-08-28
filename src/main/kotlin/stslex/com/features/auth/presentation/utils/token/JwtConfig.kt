package stslex.com.features.auth.presentation.utils.token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import stslex.com.config
import stslex.com.features.auth.presentation.utils.token.model.UserTokenModel
import java.util.*

object JwtConfig {

    const val API_KEY_HEADER = "API_KEY"
    private val PRIVATE_API_KEY = config.property("apiKey").getString()
    private val SECRET = config.property("jwt.auth.secret").getString()
    private const val ISSUER = "token is invalid"
    private const val VALIDITY_IN_MS = 36_000_00 * 24 // 1 day
    private val algorithm = Algorithm.HMAC512(SECRET)

    val verifierAuth: JWTVerifier = JWT.require(algorithm)
        .withIssuer(ISSUER)
        .build()

    fun validateKey(key: String?): Boolean = key == PRIVATE_API_KEY

    /**
     * Produce a token for this combination of name and password
     */
    fun generateToken(
        user: UserTokenModel
    ): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(ISSUER)
        .withJWTId(user.uuid)
        .withClaim("username", user.username)
        .withClaim("password", user.password)
        .withExpiresAt(getExpiration())  // optional
        .sign(algorithm)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + VALIDITY_IN_MS)
}