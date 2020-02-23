package com.davidm.payees.utils

import com.davidm.payees.R
import com.davidm.payees.entities.Payee
import com.davidm.payees.entities.PayeeAccount
import com.davidm.payees.entities.PayeeType
import org.junit.Assert.*
import org.junit.Test

class PayeesLocalMapperTest {

    private val payeeAccount = PayeeAccount("", "", null, null, null, null, null)
    private val payee =
        Payee(listOf(payeeAccount), null, null, null, null, null, "", PayeeType.BUSINESS, "", "")
    private val payeeMapper = PayeesLocalMapper()

    @Test
    fun `test business payee conversion with everything as empty or null`() {

        val mappedPayee = payeeMapper.convertPayee(payee)

        assertEquals(mappedPayee.numberOfAccounts, "1 account")

        assertEquals(mappedPayee.firstAndLastName, "Name not specified")
        assertEquals(mappedPayee.initials, "")
        assertEquals(mappedPayee.accountTypeIcon, R.drawable.ic_work_black_24dp)

    }
}