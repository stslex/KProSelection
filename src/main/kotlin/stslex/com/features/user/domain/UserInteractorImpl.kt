package stslex.com.features.user.domain

import stslex.com.datasources.user.datasource.UserDataSource
import stslex.com.features.user.domain.model.UserDomainModel
import stslex.com.features.user.domain.model.toDomain
import stslex.com.features.user.domain.model.toEntity
import stslex.com.features.user.presentation.model.UserUpdateResponse

class UserInteractorImpl(
    private val dataSource: UserDataSource
) : UserInteractor {

    override suspend fun getAll(
        page: Int,
        pageSize: Int
    ): List<UserDomainModel> = dataSource
        .getAll(
            page = page,
            pageSize = pageSize
        ).map { entity ->
            entity.toDomain()
        }

    override suspend fun getUser(
        uuid: String
    ): UserDomainModel? = dataSource.getUserByUuid(uuid)?.toDomain()

    override suspend fun updateFields(
        uuid: String,
        update: UserUpdateResponse
    ): UserDomainModel? = dataSource.updateFields(
        uuid = uuid,
        update = update.toEntity()
    )?.toDomain()
}