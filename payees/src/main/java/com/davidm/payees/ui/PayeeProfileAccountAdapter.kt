package com.davidm.payees.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davidm.payees.R
import com.davidm.payees.entities.PayeeAccount
import com.davidm.payees.utils.PayeesLocalMapper

class PayeeProfileAccountAdapter(
    val data: List<PayeeAccount>
) : RecyclerView.Adapter<PayeeProfileAccountAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.payee_profile_account_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
//        holder.mIdView.text = item.accountIdentifier

    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
//        val mIdView: TextView = mView.text_profile
    }
}
