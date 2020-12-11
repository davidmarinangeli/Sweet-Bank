package com.davidm.account.repository

import com.davidm.account.entities.User
import com.davidm.account.network.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApi: UserApi
) {

    suspend fun retrieveUser(): User {
        return withContext(Dispatchers.IO) {
            userApi.getUser()
        }
    }
}