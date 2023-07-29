package stslex.com.features.auth.utils.token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtUnAuthConfig {

    private const val UN_AUTH_SECRET = "QSCgrYA4B8gxcABV" // TODO change to property
    private const val ISSUER = "token is invalid"
    private const val PRIVATE_API_KEY = "duaVBvGw3WGJLW8A" // TODO change to property
    private const val ISSUER_UN_AUTH = "token is invalid"
    private const val VALIDITY_IN_MS = 36_000_00 * 24 // 1 day
    private val algorithm = Algorithm.HMAC512(UN_AUTH_SECRET)

    const val API_KEY_HEADER = "API_KEY"
    const val DEVICE_ID_HEADER = "DEVICE_ID"

    fun validateKey(key: String?): Boolean = key == PRIVATE_API_KEY

    val verifierUnAuth: JWTVerifier = JWT.require(algorithm)
        .withIssuer(ISSUER_UN_AUTH)
        .build()

    /**
     * Produce a token for this api_key
     */
    fun generateToken(
        apiKey: String,
        deviceId: String
    ): String = JWT.create()
        .withSubject("UnAuthToken")
        .withIssuer(ISSUER)
        .withClaim("apiKey", apiKey)
        .withClaim("deviceId", deviceId)
        .withExpiresAt(getExpiration())  // optional
        .sign(algorithm)

    /**
     * Calculate the expiration Date based on current time + the given validity
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + VALIDITY_IN_MS)
}