package com.davidm.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.davidm.account.entities.AccountHolder
import com.davidm.account.entities.User
import com.davidm.account.repository.AccountRepository
import com.davidm.account.repository.UserRepository
import com.davidm.entities.DateInterval
import com.davidm.entities.StarlingTransaction
import com.davidm.repository.TransactionsRepository
import com.davidm.utils.AmountConverter
import com.davidm.utils.DashboardLocalMapper
import com.davidm.utils.ImageUtils
import kotlinx.coroutines.*
import java.io.File


class DashboardViewModel (
    private val transactionsRepository: TransactionsRepository,
    private val accountRepository: AccountRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    private val dashboardLocalMapper = DashboardLocalMapper()
    private val converter = AmountConverter()

    val purchasesLiveData = MutableLiveData<List<DashboardLocalMapper.LocalPurchase>>()
    val accountBalanceLiveData = MutableLiveData<DashboardLocalMapper.LocalAccountBalance>()
    val userLiveData = MutableLiveData<User>()
    val profilePictureLiveData = MutableLiveData<Bitmap>()

    init {
        getAccountBalance()
    }

    fun getPurchases(dateInterval: DateInterval) {

        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {

                try {
                    val accounts = accountRepository.retrieveAccounts()

                    transactionsRepository.retrievePurchases(
                        accounts.firstOrNull()!!,
                        dateInterval.startDate,
                        dateInterval.endDate
                    )

                } catch (e: Exception) {
                    Log.e("network_error", e.message!!)
                    emptyList()
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

    private fun getAccountHolderID(): AccountHolder? {
        coroutineScope.launch {
            return@launch withContext(Dispatchers.IO) {
                try {
                    accountRepository.retrieveAccountHolder()
                } catch (e: Exception) {
                    Log.e("user_retrieve_error", e.message!!)
                }
            }
        }
        return null
    }

    private fun getProfilePicture() {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    val accountHolder = getAccountHolderID()
                    if (accountHolder != null) {
                        accountRepository.retrieveProfilePicture(accountHolder.accountHolderUid)
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    Log.e("user_retrieve_error", e.message!!)
                    null
                }
            }

            if (result != null) {
                val decodedString: ByteArray = Base64.decode(result, Base64.DEFAULT)
                val bitmap =
                    BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
                profilePictureLiveData.postValue(
                    bitmap
                )
            }
        }
    }

    fun uploadProfilePicture(bitmap: Bitmap, file: File) {

        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    val accountHolder = accountRepository.retrieveAccountHolder()
                    accountRepository.uploadProfilePicture(
                        accountHolder.accountHolderUid,
                        file
                    )
                } catch (e: Exception) {
                    Log.e("user_retrieve_error", e.message!!)
                    null
                }
            }

            // it means it's ok
            if (result == null) {
                val fixedPicture = ImageUtils.checkAndFixPhotoOrientation(
                    bitmap,
                    file
                )
                profilePictureLiveData.postValue(
                    fixedPicture
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