package stslex.com.features.user.presentation.model

import stslex.com.features.user.domain.model.UserDomainModel

fun List<UserDomainModel>.toResponse() = map { user ->
    user.toResponse()
}

fun UserDomainModel.toResponse() = UserResponse(
    uuid = uuid,
    username = username,
    nickname = nickname
)