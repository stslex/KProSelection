package stslex.com.features.auth.presentation.configure

enum class AuthConfigType(
    val configName: String,
    val realm: String
) {
    DEFAULT(
        configName = "default.auth",
        realm = "Access to request"
    ),
    JWT_TOKEN_AUTH(
        configName = "jwt.token.auth",
        realm = "Access to the '/' path"
    )
}