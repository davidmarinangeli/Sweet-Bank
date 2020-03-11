package com.davidm.payees.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.davidm.payees.R
import com.davidm.payees.utils.PayeesLocalMapper
import com.davidm.payees_profile.utils.PayeeProfileInfoConverter
import kotlinx.android.synthetic.main.activity_payee_profile.*

class PayeeProfileActivity : AppCompatActivity() {

    lateinit var payeeProfileInfoAdapter: PayeeProfileInfoAdapter
    lateinit var payeeProfileAccountAdapter: PayeeProfileAccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payee_profile)
        setSupportActionBar(toolbar)

        val payee = intent.extras?.getParcelable<PayeesLocalMapper.LocalPayee>("profileData")
        if (payee !== null) {
            val listOfValues = PayeeProfileInfoConverter().generateList(payee)
            payeeProfileInfoAdapter = PayeeProfileInfoAdapter(listOfValues)
            payeeProfileAccountAdapter = PayeeProfileAccountAdapter(payee.accounts)
        }


    }

}
