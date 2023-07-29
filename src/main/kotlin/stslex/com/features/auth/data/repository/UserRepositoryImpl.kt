package stslex.com.features.auth.data.repository

import stslex.com.datasources.user.datasource.UserDataSource
import stslex.com.features.auth.data.repository.mapper.UserRepositoryMapper.toDomain
import stslex.com.features.auth.data.repository.model.UserDataModel
import stslex.com.features.auth.domain.model.UserAuthModel

class UserRepositoryImpl(
    private val datasource: UserDataSource
) : UserRepository {
    override suspend fun isUserExist(
        username: String
    ): Boolean = datasource.isUserExist(username)

    override suspend fun saveUser(
        user: UserAuthModel
    ): UserDataModel? = datasource.saveUser(user)?.toDomain()

    override suspend fun getUser(
        username: String
    ): UserDataModel? = datasource.getUser(username)?.toDomain()

    override suspend fun getAll(): List<UserDataModel> = datasource.getAll().map { it.toDomain() }
}