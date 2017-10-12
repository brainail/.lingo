package org.brainail.EverboxingLingo.data.repository

import org.brainail.EverboxingLingo.domain.model.UserProfile
import org.brainail.EverboxingLingo.domain.repository.UserRepository

internal class UserRepositoryImpl(private val userDataSource: UserPrefDataSource) : UserRepository {
    override fun getUserProfile(): io.reactivex.Observable<UserProfile> = io.reactivex.Observable.fromCallable {
        userDataSource.getUserProfile() ?: UserProfile(defaultUserName)
    }

    override fun updateUserProfile(userProfile: UserProfile): io.reactivex.Observable<UserProfile> = io.reactivex.Observable.fromCallable {
        var profile = userProfile

        if (profile.name.isEmpty()) {
            profile = UserProfile(defaultUserName)
        }

        userDataSource.setUserProfile(profile)
        profile
    }

    override fun getDefaultUserName(): io.reactivex.Single<String> = io.reactivex.Single.fromCallable {
        defaultUserName
    }

    private val defaultUserName = "${android.os.Build.BRAND} ${android.os.Build.MODEL}"
}