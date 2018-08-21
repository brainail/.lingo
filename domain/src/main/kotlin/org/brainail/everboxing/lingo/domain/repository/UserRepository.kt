package org.brainail.everboxing.lingo.domain.repository

import io.reactivex.Observable
import io.reactivex.Single
import org.brainail.everboxing.lingo.domain.model.UserProfile

interface UserRepository {
    fun getUserProfile(): Observable<UserProfile>
    fun updateUserProfile(userProfile: UserProfile): Observable<UserProfile>
    fun getDefaultUserName(): Single<String>
}