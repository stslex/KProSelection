package stslex.com.features.auth.domain.interactor

import stslex.com.features.auth.data.repository.UserRepository
import stslex.com.features.auth.domain.model.AuthUserMapper.toDomain
import stslex.com.features.auth.presentation.model.response.UserAuthResponse
import stslex.com.features.auth.domain.model.UserAuthDomainModel
import stslex.com.features.auth.domain.model.RegisterDomainResult

class AuthInteractorImpl(
    private val repository: UserRepository,
) : AuthInteractor {

    override suspend fun register(
        user: UserAuthResponse
    ): RegisterDomainResult {
        if (repository.isUserExist(user.username)) {
            return RegisterDomainResult.UserIsExist
        }
        return repository.saveUser(user)
            ?.toDomain()
            ?.let(RegisterDomainResult::Success)
            ?: return RegisterDomainResult.SaveUserError
    }

    override suspend fun getUserByUuid(
        uuid: String
    ): UserAuthDomainModel? = repository.getUser(uuid)?.toDomain()

    override suspend fun getUserByUsername(
        username: String
    ): UserAuthDomainModel? = repository.getUser(username)?.toDomain()
}