package org.brainail.EverboxingLingo.data.repository

import io.reactivex.Observable
import io.reactivex.Observable.fromCallable
import io.reactivex.Single
import org.brainail.EverboxingLingo.data.source.UserPrefDataSource
import org.brainail.EverboxingLingo.domain.model.UserProfile
import org.brainail.EverboxingLingo.domain.repository.UserRepository

internal class UserRepositoryImpl(private val userDataSource: UserPrefDataSource) : UserRepository {
    override fun getUserProfile(): Observable<UserProfile> = fromCallable {
        userDataSource.getUserProfile() ?: UserProfile(defaultUserName)
    }

    override fun updateUserProfile(userProfile: UserProfile): Observable<UserProfile> = fromCallable {
        var profile = userProfile

        if (profile.name.isEmpty()) {
            profile = UserProfile(defaultUserName)
        }

        userDataSource.setUserProfile(profile)
        profile
    }

    override fun getDefaultUserName(): Single<String> = Single.fromCallable {
        defaultUserName
    }

    private val defaultUserName = "${android.os.Build.BRAND} ${android.os.Build.MODEL}"
}