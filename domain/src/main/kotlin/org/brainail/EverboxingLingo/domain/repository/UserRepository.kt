package org.brainail.EverboxingLingo.domain.repository

import io.reactivex.Observable
import io.reactivex.Single
import org.brainail.EverboxingLingo.domain.model.UserProfile

interface UserRepository {
    fun getUserProfile(): Observable<UserProfile>
    fun updateUserProfile(userProfile: UserProfile): Observable<UserProfile>
    fun getDefaultUserName(): Single<String>
}