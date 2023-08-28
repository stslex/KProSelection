package stslex.com.features.auth.domain.interactor

import stslex.com.features.auth.presentation.model.request.UserAuthRequest
import stslex.com.features.auth.domain.model.UserAuthDomainModel
import stslex.com.features.auth.domain.model.RegisterDomainResult

interface AuthInteractor {

    suspend fun register(user: UserAuthRequest): RegisterDomainResult

    suspend fun getUserByUuid(uuid: String): UserAuthDomainModel?

    suspend fun getUserByUsername(username: String): UserAuthDomainModel?
}