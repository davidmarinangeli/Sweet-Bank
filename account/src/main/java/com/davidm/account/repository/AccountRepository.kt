package com.davidm.account.repository

import android.util.Log
import com.davidm.account.entities.Account
import com.davidm.account.entities.AccountBalance
import com.davidm.account.entities.AccountHolder
import com.davidm.account.network.AccountApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor(
    private val accountApi: AccountApi
) {

    private var accountCachedData: List<Account>? = null

    private suspend fun getAccountCachedData(): List<Account> {
        return if (accountCachedData != null) accountCachedData!! else
            accountApi.getAccounts().accounts

    }

    suspend fun retrieveAccounts(): List<Account> {
        return if (getAccountCachedData().isNullOrEmpty()) {
            emptyList()
        } else {
            getAccountCachedData()
        }
    }

    suspend fun retrieveAccountBalance(accountId: String): AccountBalance {

        return withContext(Dispatchers.IO) {
            accountApi.getAccountBalance(accountId)
        }

    }

    suspend fun retrieveAccountHolder(): AccountHolder {
        return withContext(Dispatchers.IO) {
            accountApi.getAccountHolder()
        }
    }

    suspend fun retrieveProfilePicture(accountHolderId: String): String {
        return withContext(Dispatchers.IO) {
            accountApi.getAccountPicture(accountHolder = accountHolderId)
        }
    }

    suspend fun uploadProfilePicture(
        accountHolderId: String,
        file: File
    ): Array<String>? {
        return withContext(Dispatchers.IO) {
            accountApi.uploadAccountPicture(
                accountHolder = accountHolderId, requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            )
        }
    }
}