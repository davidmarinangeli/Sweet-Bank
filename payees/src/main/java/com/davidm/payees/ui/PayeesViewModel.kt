package com.davidm.payees.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davidm.payees.entities.ErrorMessage
import com.davidm.payees.entities.Payee
import com.davidm.payees.entities.PayeeCreationResponse
import com.davidm.payees.repository.PayeesRepository
import com.davidm.payees.utils.PayeesLocalMapper
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class PayeesViewModel @Inject constructor(
    private val payeesRepository: PayeesRepository
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    val payeesLiveData = MutableLiveData<List<PayeesLocalMapper.LocalPayee>>()
    val creationResponseLiveData = MutableLiveData<PayeeCreationResponse>()
    val mapper = PayeesLocalMapper()

    init {
        getPayees()
    }

    private fun getPayees() {

        coroutineScope.launch {

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
                    PayeeCreationResponse(null, listOf(ErrorMessage(e.message!!)), null, false)
                }
            }
            creationResponseLiveData.postValue(result)
        }

    }
}
