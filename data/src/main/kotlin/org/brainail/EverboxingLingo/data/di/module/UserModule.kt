package org.brainail.EverboxingLingo.data.di.module

import dagger.Module
import dagger.Provides
import org.brainail.EverboxingLingo.data.repository.UserPrefDataSource
import org.brainail.EverboxingLingo.data.repository.UserRepositoryImpl
import org.brainail.EverboxingLingo.domain.repository.UserRepository
import org.brainail.EverboxingLingo.domain.settings.AppSettings

@Module
internal class UserModule {
    @Provides
    fun provideUserPrefDataSource(appSettings: AppSettings): UserPrefDataSource
            = UserPrefDataSource(appSettings)

    @Provides
    fun provideUserRepository(userPrefDataSource: UserPrefDataSource): UserRepository
            = UserRepositoryImpl(userPrefDataSource)
}