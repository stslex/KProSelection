package stslex.com.features.user.domain

import stslex.com.features.user.domain.model.UserDomainModel
import stslex.com.features.user.presentation.model.UserUpdateResponse

interface UserInteractor {

    suspend fun getAll(page: Int, pageSize: Int): List<UserDomainModel>

    suspend fun getUser(uuid: String): UserDomainModel?

    suspend fun updateFields(
        uuid: String,
        update: UserUpdateResponse
    ): UserDomainModel
}