package com.davidm.account.repository

import android.util.Log
import com.davidm.account.entities.Account
import com.davidm.account.entities.AccountBalance
import com.davidm.account.network.AccountApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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
        return if (getAccountCachedData().isNullOrEmpty()){
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
}