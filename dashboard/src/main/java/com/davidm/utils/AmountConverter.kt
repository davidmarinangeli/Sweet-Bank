package com.davidm.utils


class AmountConverter {

    fun convertAmountToDouble(amount: Int): Double {
        return amount.toDouble() / 100.0
    }

    fun convertAmountToInt(amount: Double): Int {
        return (amount * 100).toInt()
    }

}