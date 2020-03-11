package com.davidm.payees.utils

import android.os.Parcelable
import com.davidm.account.entities.Account
import com.davidm.account.entities.AccountBalance
import com.davidm.payees.R
import com.davidm.payees.entities.Payee
import com.davidm.payees.entities.PayeeAccount
import com.davidm.payees.entities.PayeeType
import kotlinx.android.parcel.Parcelize
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class PayeesLocalMapper {

    @Parcelize
    data class LocalPayee(
        val payeeName: String,
        val firstAndLastName: String,
        val numberOfAccounts: String,
        val accounts: List<PayeeAccount>,
        val accountTypeIcon: Int,
        val initials: String,
        val businessName: String?,
        val phoneNumber: String?

    ) : Parcelable

    fun convertPayee(payee: Payee): LocalPayee {

        var firstAndLastName = "Name not specified"
        var initials = ""
        if (payee.firstName !== null && payee.lastName !== null) {
            firstAndLastName = "${payee.firstName} ${payee.lastName}"
            initials = "${payee.firstName[0]}${payee.lastName[0]}"
        } else if(payee.payeeName.isNotEmpty()){
            initials = "${payee.payeeName[0]}${payee.payeeName[1]}"
        } else {
            initials = ""
        }
        val numberOfAccounts = if (payee.accounts.size > 1) {
            "${payee.accounts.size} accounts"
        } else {
            "1 account"
        }

        val payeeType = when (payee.payeeType) {
            PayeeType.INDIVIDUAL -> R.drawable.ic_person_black_24dp
            PayeeType.BUSINESS -> R.drawable.ic_work_black_24dp
            null -> R.drawable.ic_person_black_24dp
        }

        return LocalPayee(
            payeeName = payee.payeeName,
            firstAndLastName = firstAndLastName,
            numberOfAccounts = numberOfAccounts,
            accountTypeIcon = payeeType,
            initials = initials,
            businessName = payee.businessName,
            phoneNumber = payee.phoneNumber,
            accounts = payee.accounts
        )

    }
}