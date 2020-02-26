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
        val amountColor: Int,
        val spendingCategoryIcon: Int,
        val date: String
    )

    data class LocalAccountBalance(
        val amount: String,
        val amountCents: String,
        val currency: String
    )

    fun convertPurchases(converter: AmountConverter, purchase: Purchase): LocalPurchase {

        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val resultFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        formatter.currency = Currency.getInstance(purchase.amount.currency)
        val finalDate = dateFormatter.parse(purchase.transactionTime)

        val icon: Int = when (purchase.spendingCategory) {
            SpendingCategory.EATING_OUT -> R.drawable.hamburger_solid
            SpendingCategory.INCOME -> R.drawable.money_wave
            SpendingCategory.PAYMENTS -> R.drawable.money_wave
            SpendingCategory.GENERAL -> R.drawable.coins_solid
        }

        val absoluteAmount =
            formatter.format(converter.convertAmountToDouble(purchase.amount.minorUnits))
        val amount = when (purchase.spendingCategory) {
            SpendingCategory.INCOME -> "+ $absoluteAmount"
            else -> "- $absoluteAmount"
        }

        val amountColor = when (purchase.spendingCategory) {
            SpendingCategory.INCOME -> R.color.positiveAmountGreen
            else -> R.color.negativeAmountRed
        }

        return LocalPurchase(
            counterPartyName = purchase.counterPartyName,
            amount = amount,
            amountColor = amountColor,
            spendingCategoryIcon = icon,
            date = resultFormat.format(finalDate!!)

        )

    }

    fun convertBalanceAmount(
        converter: AmountConverter,
        balance: AccountBalance
    ): LocalAccountBalance {

        val formatter = NumberFormat.getCurrencyInstance()
        formatter.currency = Currency.getInstance(balance.amount.currency)

        val amount: String =
            formatter.format(converter.convertAmountToDouble(balance.amount.minorUnits))

        val amountMain = amount.split('.')

        return LocalAccountBalance(
            amount = amountMain[0],
            amountCents = ".${amountMain[1]}",
            currency = balance.amount.currency
        )
    }
}