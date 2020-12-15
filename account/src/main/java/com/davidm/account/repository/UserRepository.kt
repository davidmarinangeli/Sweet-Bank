package com.davidm.account.repository

import com.davidm.account.entities.User
import com.davidm.account.network.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository (
    private val userApi: UserApi
) {

    suspend fun retrieveUser(): User {
        return withContext(Dispatchers.IO) {
            userApi.getUser()
        }
    }
}