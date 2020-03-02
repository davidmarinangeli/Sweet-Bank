package com.davidm.payees.repository

import androidx.lifecycle.MutableLiveData
import com.davidm.payees.entities.ErrorMessage
import com.davidm.payees.entities.Payee
import com.davidm.payees.entities.PayeeCreationResponse
import com.davidm.payees.entities.defaultError
import com.davidm.payees.network.PayeesApi
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PayeesRepository @Inject constructor(
    private val payeesApi: PayeesApi
) {

    private val payeesLiveData = MutableLiveData<List<Payee>>()

    /**
     * This method will retrieve the list of Payees for the account
     */
    suspend fun retrievePayees(
    ): List<Payee> {
        val result = withContext(Dispatchers.IO) {
            return@withContext payeesApi.getPayees().payees
        }
        payeesLiveData.postValue(result)
        return result
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

    fun observeResult() = payeesLiveData

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