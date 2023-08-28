package stslex.com.features.user.presentation.model

import stslex.com.features.user.domain.model.UserDomainModel

fun List<UserDomainModel>.toRespond() = map { it.toRespond() }

fun UserDomainModel.toRespond() = UserRespond(
    username = username,
    nickname = nickname
)