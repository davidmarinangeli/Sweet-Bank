package com.davidm.utils

import com.davidm.account.entities.AccountBalance
import com.davidm.entities.Purchase
import com.davidm.entities.SpendingCategory
import com.davidm.ui.R
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class DashboardLocalMapper {

    data class LocalPurchase(
        val counterPartyName: String,
        val amount: String,
        val spendingCategoryColor: Int,
        val date: String
    )

    fun convertPurchases(converter: AmountConverter, purchase: Purchase): LocalPurchase {

        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val resultFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        formatter.currency = Currency.getInstance(purchase.amount.currency)
        val finalDate = dateFormatter.parse(purchase.transactionTime)

        val color: Int = when (purchase.spendingCategory) {
            SpendingCategory.EATING_OUT -> R.color.orangeLowOpacity
            SpendingCategory.INCOME -> R.color.greenLowOpacity
            SpendingCategory.PAYMENTS -> R.color.lightBlueLowOpacity
            SpendingCategory.GENERAL -> R.color.colorPrimaryDark
        }

        val absoluteAmount =
            formatter.format(converter.convertAmountToDouble(purchase.amount.minorUnits))
        val amount = when (purchase.spendingCategory) {
            SpendingCategory.INCOME -> "+ $absoluteAmount"
            else -> "- $absoluteAmount"
        }

        return LocalPurchase(
            counterPartyName = purchase.counterPartyName,
            amount = amount,
            spendingCategoryColor = color,
            date = resultFormat.format(finalDate!!)

        )

    }

    fun convertBalanceAmount(converter: AmountConverter, balance: AccountBalance): String {

        val formatter = NumberFormat.getCurrencyInstance()
        formatter.currency = Currency.getInstance(balance.amount.currency)

        return formatter.format(converter.convertAmountToDouble(balance.amount.minorUnits))
    }
}