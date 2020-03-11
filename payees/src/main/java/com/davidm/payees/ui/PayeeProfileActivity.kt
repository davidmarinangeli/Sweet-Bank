package com.davidm.payees.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.davidm.payees.R
import com.davidm.payees.utils.PayeesLocalMapper
import com.davidm.payees_profile.utils.PayeeProfileInfoConverter
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.activity_payee_profile.*


class PayeeProfileActivity : AppCompatActivity() {

    private lateinit var payeeProfileInfoAdapter: PayeeProfileInfoAdapter
    private lateinit var payeeProfileAccountAdapter: PayeeProfileAccountAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payee_profile)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val infoRecyclerView = findViewById<RecyclerView>(R.id.profile_info_list)
        infoRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        val accountRecyclerView = findViewById<RecyclerView>(R.id.profile_accounts_list)
        accountRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        val payee = intent.extras?.getParcelable<PayeesLocalMapper.LocalPayee>("profileData")
        if (payee !== null) {

            supportActionBar?.title = payee.payeeName

            val listOfValues = PayeeProfileInfoConverter().generateList(payee)
            payeeProfileInfoAdapter = PayeeProfileInfoAdapter(listOfValues)
            infoRecyclerView.adapter = payeeProfileInfoAdapter
            payeeProfileAccountAdapter = PayeeProfileAccountAdapter(payee.accounts)
            accountRecyclerView.adapter = payeeProfileAccountAdapter

        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }

}
