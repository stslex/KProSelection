package stslex.com.features.auth.presentation.token

interface TokenGenerator {

    fun generateToken(user: UserTokenModel): String
}

