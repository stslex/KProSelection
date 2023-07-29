package stslex.com.domain.interactor

import stslex.com.data.repository.UserRepository
import stslex.com.data.repository.model.UserAuthModel
import stslex.com.domain.model.AuthResult
import stslex.com.domain.model.AuthUserMapper.toResponse
import stslex.com.domain.model.RegisterResult
import stslex.com.domain.utils.PasswordChecker

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

        val response = repository.saveUser(user)
            ?.toResponse()
            ?: return RegisterResult.SaveUserError

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

        // TODO Generate token for user
        return AuthResult.Success(userSource.toResponse())
    }
}