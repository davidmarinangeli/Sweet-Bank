package com.davidm.payees

import com.davidm.payees.entities.Payee
import com.davidm.payees.entities.PayeeAccount
import com.davidm.payees.entities.PayeeType
import com.davidm.payees.entities.defaultAccount
import com.davidm.payees.utils.PayeeProfileInfoConverter
import com.davidm.payees.utils.PayeesLocalMapper
import junit.framework.Assert.assertEquals
import org.junit.Test

class PayeeProfileTest {

    private val payee =
        Payee(
            listOf(defaultAccount),
            "businessName",
            "1990-12-12",
            "firstName",
            "lastName",
            null,
            "payeeName",
            PayeeType.BUSINESS,
            "qwe",
            "123123123"
        )
    private val infoConverter: PayeeProfileInfoConverter = PayeeProfileInfoConverter()
    private val localMapper: PayeesLocalMapper = PayeesLocalMapper()


    @Test
    fun `test payee profile`() {

        val localPayeeList = localMapper.convertPayee(payee)
        val profileInfoList = infoConverter.generateList(localPayeeList)

        assertEquals(profileInfoList.size, 4)
        assertEquals(profileInfoList[0].text, localPayeeList.firstAndLastName)
        assertEquals(profileInfoList[0].icon, R.drawable.ic_person_black_24dp)

        assertEquals(profileInfoList[1].text, localPayeeList.phoneNumber)
        assertEquals(profileInfoList[1].icon, R.drawable.ic_phone_black_24dp)

        assertEquals(profileInfoList[2].text, localPayeeList.dateOfBirth)
        assertEquals(profileInfoList[2].icon, R.drawable.ic_perm_contact_calendar_black_24dp)

        assertEquals(profileInfoList[2].text, localPayeeList.businessName)
        assertEquals(profileInfoList[2].icon, R.drawable.ic_work_black_24dp)

    }
}