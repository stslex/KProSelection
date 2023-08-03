package stslex.com.features.auth.presentation.utils.token

object AuthCache {

    private var _tokens: MutableMap<String, String> = mutableMapOf()
    val tokens: Map<String, String>
        get() = _tokens

    fun setToken(
        uuid: String,
        token: String
    ) {
        _tokens[uuid] = token
    }

    fun isValid(
        jwtId: String?,
        uuid: String?
    ): Boolean {
        val token = jwtId
            ?.let(JwtConfig::getTokenById)
            ?: return false
        val currentUuid = uuid ?: return false
        val userToken = tokens[currentUuid] ?: return false
        return token == userToken
    }
}