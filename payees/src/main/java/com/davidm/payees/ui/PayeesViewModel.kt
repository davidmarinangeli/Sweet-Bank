package com.davidm.payees.ui

import android.util.Log
import androidx.lifecycle.*
import androidx.test.espresso.idling.CountingIdlingResource
import com.davidm.payees.entities.ErrorMessage
import com.davidm.payees.entities.Payee
import com.davidm.payees.entities.PayeeCreationResponse
import com.davidm.payees.entities.defaultError
import com.davidm.payees.repository.PayeesRepository
import com.davidm.payees.utils.PayeesLocalMapper
import com.davidm.payees.utils.launchIdling
import com.squareup.moshi.Moshi
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

class PayeesViewModel constructor(
    private val payeesRepository: PayeesRepository
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    var payeesLiveData = MutableLiveData<List<PayeesLocalMapper.LocalPayee>>()
    val creationResponseLiveData = MutableLiveData<PayeeCreationResponse>()
    val mapper = PayeesLocalMapper()


    init {
        getPayees()
    }

    fun getPayees() {

        coroutineScope.launchIdling {

            val result = withContext(Dispatchers.IO) {

                try {
                    payeesRepository.retrievePayees()

                } catch (e: Exception) {
                    Log.e("network_error", e.message!!)
                    emptyList<Payee>()
                }
            }
            payeesLiveData.postValue(result.map { mapper.convertPayee(it) })

        }
    }

    fun createPayee(payee: Payee) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    payeesRepository.createPayee(payee)
                } catch (e: Exception) {
                    Log.e("network_error", e.message!!)
                    PayeeCreationResponse(
                        null,
                        listOf(defaultError),
                        null,
                        false
                    )
                }
            }
            creationResponseLiveData.postValue(result)
        }

    }

}
