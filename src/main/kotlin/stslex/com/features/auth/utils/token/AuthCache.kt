package stslex.com.features.auth.utils.token

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
}