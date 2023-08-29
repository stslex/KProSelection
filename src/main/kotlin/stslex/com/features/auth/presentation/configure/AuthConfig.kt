package stslex.com.features.auth.presentation.configure

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import stslex.com.config

object AuthConfig {

    private val PRIVATE_API_KEY = config.property("apiKey").getString()
    private val SECRET = config.property("jwt.auth.secret").getString()
    const val ISSUER = "token is invalid"
    const val VALIDITY_IN_MS = 36_000_00 * 24
    const val API_KEY_HEADER_NAME = "x-api-key"
    val algorithm = Algorithm.HMAC512(SECRET)

    val verifierAuth: JWTVerifier = JWT.require(algorithm)
        .withIssuer(ISSUER)
        .build()

    fun isKeyValid(key: String?): Boolean = key == PRIVATE_API_KEY
}