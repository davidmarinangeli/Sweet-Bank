package com.davidm.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davidm.account.entities.User
import com.davidm.account.repository.AccountRepository
import com.davidm.account.repository.UserRepository
import com.davidm.entities.DateInterval
import com.davidm.entities.StarlingTransaction
import com.davidm.repository.DashboardRepository
import com.davidm.utils.AmountConverter
import com.davidm.utils.DashboardLocalMapper
import kotlinx.coroutines.*
import java.lang.Exception
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository,
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private val dashboardLocalMapper = DashboardLocalMapper()
    private val converter = AmountConverter()

    val purchasesLiveData = MutableLiveData<List<DashboardLocalMapper.LocalPurchase>>()
    val accountBalanceLiveData = MutableLiveData<DashboardLocalMapper.LocalAccountBalance>()
    val userLiveData = MutableLiveData<User>()

    init {
        getAccountBalance()
    }

    fun getPurchases(dateInterval: DateInterval) {

        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {

                try {
                    val accounts = accountRepository.retrieveAccounts()

                    dashboardRepository.retrievePurchases(
                        accounts.firstOrNull()!!,
                        dateInterval.startDate,
                        dateInterval.endDate
                    )

                } catch (e: Exception) {
                    Log.e("network_error", e.message!!)
                    emptyList<StarlingTransaction>()
                }
            }
            updateView(starlingTransactionList = result)
        }
    }

    fun getUserInfo() {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    userRepository.retrieveUser()
                } catch (e: Exception) {
                    Log.e("user_retrieve_error", e.message!!)
                    null
                }
            }

            if (result != null) {
                userLiveData.postValue(
                    result
                )
            }
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


    private fun updateView(starlingTransactionList: List<StarlingTransaction>) {
        purchasesLiveData.postValue(starlingTransactionList.map {
            dashboardLocalMapper.convertPurchases(converter, it)
        })
    }

}