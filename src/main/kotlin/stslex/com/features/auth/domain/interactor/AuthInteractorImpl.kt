package stslex.com.features.auth.domain.interactor

import stslex.com.features.auth.data.repository.UserRepository
import stslex.com.features.auth.domain.model.AuthUserMapper.toDomain
import stslex.com.features.auth.domain.model.AuthUserMapper.toResponse
import stslex.com.features.auth.domain.model.UserAuthModel
import stslex.com.features.auth.domain.model.UserDomainModel
import stslex.com.features.auth.domain.result.AuthResult
import stslex.com.features.auth.domain.result.RegisterResult
import stslex.com.features.auth.utils.PasswordChecker
import stslex.com.features.auth.utils.token.JwtConfig
import stslex.com.features.auth.utils.token.model.UserTokenMapper.toToken
import stslex.com.features.auth.utils.token.model.UserTokenModel

class AuthInteractorImpl(
    private val repository: UserRepository,
    private val passwordChecker: PasswordChecker
) : AuthInteractor {

    override suspend fun register(user: UserAuthModel): RegisterResult {
        if (passwordChecker.isValid(user.password).not()) {
            return RegisterResult.InvalidPassword
        }

        if (repository.isUserExist(user.username)) {
            return RegisterResult.UserIsExist
        }

        val userSource = repository.saveUser(user)
            ?: return RegisterResult.SaveUserError

        val token = JwtConfig.generateToken(userSource.toToken())
        val response = userSource.toResponse(token)

        return RegisterResult.Success(response)
    }

    override suspend fun auth(
        user: UserAuthModel
    ): AuthResult {
        if (passwordChecker.isValid(user.password).not()) {
            return AuthResult.InvalidPassword
        }

        val userSource = repository.getUser(user.username)
            ?: return AuthResult.UserIsNotExist

        if (userSource.password != user.password) {
            return AuthResult.InvalidPassword
        }

        val token = JwtConfig.generateToken(userSource.toToken())
        val response = userSource.toResponse(token)
        return AuthResult.Success(response)
    }

    override suspend fun getUserTokenModel(uuid: String): UserTokenModel? = repository.getUserByUuid(uuid)?.toToken()

    override suspend fun getAll(): List<UserDomainModel> = repository.getAll().toDomain()

    override suspend fun clearAll() {
        repository.clearAll()
    }
}