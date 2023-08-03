package stslex.com.features.auth.domain.interactor

import stslex.com.features.auth.presentation.model.response.UserAuthResponse
import stslex.com.features.auth.domain.model.UserAuthDomainModel
import stslex.com.features.auth.domain.model.RegisterDomainResult

interface AuthInteractor {

    suspend fun register(user: UserAuthResponse): RegisterDomainResult

    suspend fun getUserByUuid(uuid: String): UserAuthDomainModel?

    suspend fun getUserByUsername(username: String): UserAuthDomainModel?
}