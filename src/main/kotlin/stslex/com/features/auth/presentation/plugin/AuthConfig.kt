package stslex.com.features.auth.presentation.plugin

enum class AuthConfig(
    val configName: String,
    val realm: String
) {
    JWT_TOKEN(
        configName = "jwt.token",
        realm = "Access to the auth path"
    ),
    JWT_TOKEN_AUTH(
        configName = "jwt.token.auth",
        realm = "Access to the '/' path"
    )
}