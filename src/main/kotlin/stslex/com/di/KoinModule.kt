package stslex.com.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import stslex.com.data.datasource.UserDataSource
import stslex.com.data.datasource.UserDataSourceImpl
import stslex.com.data.repository.UserRepository
import stslex.com.data.repository.UserRepositoryImpl
import stslex.com.domain.interactor.AuthInteractor
import stslex.com.domain.interactor.AuthInteractorImpl
import stslex.com.domain.utils.PasswordChecker
import stslex.com.domain.utils.PasswordCheckerImpl

val koinModule = module {
    singleOf(::UserDataSourceImpl) { bind<UserDataSource>() }
    singleOf(::UserRepositoryImpl) { bind<UserRepository>() }
    singleOf(::AuthInteractorImpl) { bind<AuthInteractor>() }
    singleOf(::PasswordCheckerImpl) { bind<PasswordChecker>() }
}