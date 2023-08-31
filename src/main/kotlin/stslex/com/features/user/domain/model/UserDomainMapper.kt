package stslex.com.features.user.domain.model

import stslex.com.datasources.user.model.UserEntity
import stslex.com.datasources.user.model.UserUpdateEntity
import stslex.com.features.user.presentation.model.UserUpdateRequest

fun UserEntity.toDomain() = UserDomainModel(
    uuid = uuid,
    username = username,
    nickname = nickname
)

fun UserUpdateRequest.toEntity() = UserUpdateEntity(nickname = nickname)