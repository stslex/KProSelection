package stslex.com.features.auth.domain.model

sealed interface RegisterDomainResult {

    data class Success(
        val data: UserAuthDomainModel
    ) : RegisterDomainResult

    data object UserIsExist : RegisterDomainResult

    data object SaveUserError : RegisterDomainResult

    val takeIfSuccess: UserAuthDomainModel?
        get() = if (this is Success) data else null
}