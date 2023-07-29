package stslex.com.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import stslex.com.datasources.user.datasource.UserDataSource
import stslex.com.datasources.user.datasource.UserDataSourceImpl
import stslex.com.features.auth.data.repository.UserRepository
import stslex.com.features.auth.data.repository.UserRepositoryImpl
import stslex.com.features.auth.domain.interactor.AuthInteractor
import stslex.com.features.auth.domain.interactor.AuthInteractorImpl
import stslex.com.features.auth.utils.PasswordChecker
import stslex.com.features.auth.utils.PasswordCheckerImpl

val koinModule = module {
    singleOf(::UserDataSourceImpl) { bind<UserDataSource>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::AuthInteractorImpl) { bind<AuthInteractor>() }
    singleOf(::PasswordCheckerImpl) { bind<PasswordChecker>() }
}
