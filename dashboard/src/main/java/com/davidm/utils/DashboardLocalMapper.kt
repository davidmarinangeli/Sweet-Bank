package com.davidm.utils

import com.davidm.account.entities.AccountBalance
import com.davidm.entities.StarlingTransaction
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
        val spendingCategoryColor: Int,
        val date: String,
        val spendingCategoryText: String
    )

    data class LocalAccountBalance(
        val amount: String,
        val amountCents: String,
        val currency: String
    )

    fun convertPurchases(
        converter: AmountConverter,
        starlingTransaction: StarlingTransaction
    ): LocalPurchase {

        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val resultFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        formatter.currency = Currency.getInstance(starlingTransaction.amount.currency)
        val finalDate = dateFormatter.parse(starlingTransaction.transactionTime)
        val finalDateString = if (finalDate != null) {
            resultFormat.format(finalDate)
        } else {
            "Error"
        }

        val spendingCategoryColor: Int = when (starlingTransaction.spendingCategory) {
            SpendingCategory.EATING_OUT -> R.color.colorAccent
            SpendingCategory.INCOME -> R.color.colorPrimaryDark
            SpendingCategory.PAYMENTS -> R.color.spendingCategoryPink
            SpendingCategory.GENERAL -> R.color.colorPrimary
        }

        val spendingCategoryText: String = when (starlingTransaction.spendingCategory) {
            SpendingCategory.EATING_OUT -> "Eating Out"
            SpendingCategory.INCOME -> "Income"
            SpendingCategory.PAYMENTS -> "Payments"
            SpendingCategory.GENERAL -> "General"
        }

        val absoluteAmount =
            formatter.format(converter.convertAmountToDouble(starlingTransaction.amount.minorUnits))
        val amount = when (starlingTransaction.spendingCategory) {
            SpendingCategory.INCOME -> "+ $absoluteAmount"
            else -> "- $absoluteAmount"
        }

        val amountColor = when (starlingTransaction.spendingCategory) {
            SpendingCategory.INCOME -> R.color.positiveAmountGreen
            else -> R.color.negativeAmountRed
        }

        return LocalPurchase(
            counterPartyName = starlingTransaction.counterPartyName,
            amount = amount,
            amountColor = amountColor,
            spendingCategoryColor = spendingCategoryColor,
            date = finalDateString,
            spendingCategoryText = spendingCategoryText

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