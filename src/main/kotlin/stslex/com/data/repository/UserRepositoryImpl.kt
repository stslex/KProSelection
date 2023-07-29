package stslex.com.data.repository

import stslex.com.data.datasource.UserDataSource
import stslex.com.data.repository.mapper.UserRepositoryMapper.toDomain
import stslex.com.data.repository.model.UserAuthModel
import stslex.com.data.repository.model.UserModel

class UserRepositoryImpl(
    private val datasource: UserDataSource
) : UserRepository {
    override suspend fun isUserExist(
        username: String
    ): Boolean = datasource.isUserExist(username)

    override suspend fun saveUser(
        user: UserAuthModel
    ): UserModel? = datasource.saveUser(user)?.toDomain()

    override suspend fun getUser(
        username: String
    ): UserModel? = datasource.getUser(username)?.toDomain()
}