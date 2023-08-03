package stslex.com.features.user.domain.model

import stslex.com.datasources.user.model.UserEntity
import stslex.com.datasources.user.model.UserUpdateEntity
import stslex.com.features.user.presentation.model.UserUpdateResponse

fun UserEntity.toDomain() = UserDomainModel(
    uuid = uuid,
    username = username,
    nickname = nickname
)

fun UserUpdateResponse.toEntity() = UserUpdateEntity(nickname = nickname)