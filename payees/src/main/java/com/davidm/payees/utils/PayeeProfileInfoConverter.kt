package com.davidm.payees_profile.utils

import com.davidm.payees.utils.PayeesLocalMapper

class PayeeProfileInfoConverter {

    data class PayeeProfileInfo(
        val text: String,
        val icon: Int
    )

    fun generateList(localPayee: PayeesLocalMapper.LocalPayee): List<PayeeProfileInfo> {
        val list: MutableList<PayeeProfileInfo> = listOf<PayeeProfileInfo>().toMutableList()

        list.add(
            PayeeProfileInfo(
                localPayee.firstAndLastName,
                com.davidm.resources.R.drawable.ic_person_black_24dp
            )
        )

        if (!localPayee.phoneNumber.isNullOrBlank()) {
            list.add(
                PayeeProfileInfo(
                    localPayee.phoneNumber,
                    com.davidm.resources.R.drawable.ic_phone_black_24dp
                )
            )
        }

        if (!localPayee.businessName.isNullOrBlank()) {
            list.add(
                PayeeProfileInfo(
                    localPayee.businessName,
                    com.davidm.resources.R.drawable.ic_work_black_24dp
                )
            )
        }

        return list
    }
}