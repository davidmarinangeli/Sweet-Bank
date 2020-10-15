package com.davidm.repository

import com.davidm.account.entities.Account
import com.davidm.entities.StarlingTransaction
import com.davidm.network.DashboardApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DashboardRepository @Inject constructor(
    private val dashboardApi: DashboardApi
) {

    /**
     * This method will retrieve the list of transactions for a specific account
     */
    suspend fun retrievePurchases(
        account: Account,
        startDate: String,
        endDate: String
    ): List<StarlingTransaction> {

        return withContext(Dispatchers.IO) {
            return@withContext dashboardApi.getPurchasesInAWeek(
                account.accountUid,
                account.defaultCategory,
                startDate,
                endDate
            ).feedItems
        }

    }
}