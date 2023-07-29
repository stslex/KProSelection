package stslex.com.features.auth.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import stslex.com.features.auth.domain.model.UserAuthModel
import java.util.*

object JwtConfig {

    private const val SECRET = "test_secret" // TODO change to property
    private const val ISSUER = "token is invalid"
    private const val VALIDITY_IN_MS = 36_000_00 * 24 // 1 day
    private val algorithm = Algorithm.HMAC512(SECRET)

    val verifierAuth: JWTVerifier = JWT.require(algorithm)
        .withIssuer(ISSUER)
        .build()

    /**
     * Produce a token for this combination of name and password
     */
    fun generateToken(user: UserAuthModel): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(ISSUER)
        .withClaim("username", user.username)
        .withClaim("password", user.password)
        .withExpiresAt(getExpiration())  // optional
        .sign(algorithm)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + VALIDITY_IN_MS)
}