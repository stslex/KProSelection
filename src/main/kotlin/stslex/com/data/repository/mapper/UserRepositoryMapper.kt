package stslex.com.data.repository.mapper

import stslex.com.data.datasource.model.UserEntity
import stslex.com.data.repository.model.UserModel

object UserRepositoryMapper {

    fun UserEntity.toDomain(): UserModel = UserModel(
        uuid = uuid,
        username = username,
        nickname = nickname,
        password = password
    )
}