package com.davidm.payees.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.davidm.payees.R
import com.davidm.payees.utils.CollapsingBarListener
import com.davidm.payees.utils.PayeeProfileInfoConverter
import com.davidm.payees.utils.PayeesLocalMapper
import com.google.android.material.appbar.AppBarLayout
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

        val payee = intent.extras?.getParcelable<PayeesLocalMapper.LocalPayee>("profileData")

        app_bar.addOnOffsetChangedListener(object: CollapsingBarListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when(state){
                    State.COLLAPSED -> toolbar_layout.title = payee?.payeeName
                    State.EXPANDED -> toolbar_layout.title = "${payee?.payeeName?.take(12)}..."
                    else -> null
                }
            }
        })

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
