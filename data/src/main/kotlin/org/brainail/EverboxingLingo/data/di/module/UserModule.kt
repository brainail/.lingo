package org.brainail.EverboxingLingo.data.di.module

import dagger.Module
import dagger.Provides
import org.brainail.EverboxingLingo.data.repository.UserRepositoryImpl
import org.brainail.EverboxingLingo.data.source.UserPrefDataSource
import org.brainail.EverboxingLingo.domain.repository.UserRepository
import org.brainail.EverboxingLingo.domain.settings.UserSettings

@Module
internal class UserModule {
    @Provides
    fun provideUserPrefDataSource(userSettings: UserSettings): UserPrefDataSource
            = UserPrefDataSource(userSettings)

    @Provides
    fun provideUserRepository(userPrefDataSource: UserPrefDataSource): UserRepository
            = UserRepositoryImpl(userPrefDataSource)
}