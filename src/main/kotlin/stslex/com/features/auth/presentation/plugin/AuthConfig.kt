package stslex.com.features.auth.presentation.plugin

enum class AuthConfig(
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