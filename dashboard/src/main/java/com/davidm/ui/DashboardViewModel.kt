package com.davidm.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davidm.account.entities.AccountBalance
import com.davidm.account.repository.AccountRepository
import com.davidm.entities.Purchase
import com.davidm.repository.DashboardRepository
import com.davidm.utils.AmountConverter
import com.davidm.utils.DashboardLocalMapper
import kotlinx.coroutines.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository,
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private val dashboardLocalMapper = DashboardLocalMapper()
    private val converter = AmountConverter()

    val purchasesLiveData = MutableLiveData<List<DashboardLocalMapper.LocalPurchase>>()
    val accountBalanceLiveData = MutableLiveData<String>()

    init {
        getAccountBalance()
        getPurchases()
    }

    private fun getPurchases() {

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())

        val endDate = calendar.time

        val startDate = run {
            calendar.add(Calendar.DATE, -5)
            calendar.time
        }

        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {

                try {

                    val accounts = accountRepository.retrieveAccounts()

                    dashboardRepository.retrievePurchases(
                        accounts.firstOrNull()!!,
                        dateFormat.format(startDate),
                        dateFormat.format(endDate)
                    )

                } catch (e: Exception) {
                    Log.e("network_error", e.message!!)
                    emptyList<Purchase>()
                }
            }
            updateView(purchaseList = result)
        }
    }

    private fun getAccountBalance() {

        coroutineScope.launch {

            val result = withContext(Dispatchers.IO) {
                try {

                    val accounts = accountRepository.retrieveAccounts()
                    if (accounts.isNullOrEmpty()) {
                        null
                    } else {
                        accountRepository.retrieveAccountBalance(accounts.firstOrNull()!!.accountUid)
                    }


                } catch (e: Exception) {
                    Log.e("account_retrieve_error", e.message!!)
                    null

                }
            }

            if (result != null) {
                accountBalanceLiveData.postValue(
                    dashboardLocalMapper.convertBalanceAmount(
                        converter,
                        result
                    )
                )
            }
        }

    }


    private fun updateView(purchaseList: List<Purchase>) {
        purchasesLiveData.postValue(purchaseList.map {
            dashboardLocalMapper.convertPurchases(converter, it)
        })
    }

}