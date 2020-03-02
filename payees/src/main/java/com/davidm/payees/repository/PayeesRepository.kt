package com.davidm.payees.repository

import com.davidm.payees.entities.ErrorMessage
import com.davidm.payees.entities.Payee
import com.davidm.payees.entities.PayeeCreationResponse
import com.davidm.payees.entities.defaultError
import com.davidm.payees.network.PayeesApi
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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
            val response = payeesApi.createPayee(body = payee)
            if (response.isSuccessful && response.body() !== null) {
                response.body()!!
            } else {

                PayeeCreationResponse(
                    null,
                    listOf(convertErrorBody(response.errorBody())),
                    null,
                    false
                )
            }

        }

    }

    // hmm, I should use the moshi instance that Dagger provides!
    private fun convertErrorBody(throwable: ResponseBody?): ErrorMessage {

        if (throwable == null) {
            return defaultError
        }

        return try {
            throwable.source().let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorMessage::class.java)
                moshiAdapter.fromJson(it)
            }!!
        } catch (exception: Exception) {
            defaultError
        }
    }
}