package com.davidm.payees.repository

import com.davidm.payees.entities.Payee
import com.davidm.payees.entities.PayeeCreationResponse
import com.davidm.payees.network.PayeesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PayeesRepository @Inject constructor(
    private val payeesApi: PayeesApi
) {

    /**
     * This method will retrieve the list of Payees for the account
     */
    suspend fun retrievePayees(
    ): List<Payee> {
        return withContext(Dispatchers.IO) {
            return@withContext payeesApi.getPayees().payees
        }

    }

    /**
     * This method will create a new Payee for the account
     */
    suspend fun createPayee(
        payee: Payee
    ): PayeeCreationResponse {
        return withContext(Dispatchers.IO) {
            return@withContext payeesApi.createPayee(body = payee)
        }

    }
}