package com.davidm.payees.utils

import com.davidm.resources.R

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
                R.drawable.ic_person_black_24dp
            )
        )

        if (!localPayee.phoneNumber.isNullOrBlank()) {
            list.add(
                PayeeProfileInfo(
                    localPayee.phoneNumber,
                    R.drawable.ic_phone_black_24dp
                )
            )
        }

        if (!localPayee.dateOfBirth.isNullOrBlank()) {
            list.add(
                PayeeProfileInfo(
                    localPayee.dateOfBirth,
                    R.drawable.ic_perm_contact_calendar_black_24dp
                )
            )
        }

        if (!localPayee.businessName.isNullOrBlank()) {
            list.add(
                PayeeProfileInfo(
                    localPayee.businessName,
                    R.drawable.ic_work_black_24dp
                )
            )
        }

        return list
    }
}