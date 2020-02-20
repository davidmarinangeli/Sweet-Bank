package com.davidm.repository

import com.davidm.account.entities.Account
import com.davidm.entities.Purchase
import com.davidm.network.DashboardApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant
import java.util.*
import javax.inject.Inject

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
    ): List<Purchase> {

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