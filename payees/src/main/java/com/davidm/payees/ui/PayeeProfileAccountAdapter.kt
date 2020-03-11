package com.davidm.payees.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.davidm.payees.R
import com.davidm.payees.entities.PayeeAccount
import com.davidm.payees.utils.PayeesLocalMapper
import kotlinx.android.synthetic.main.payee_profile_account_item.view.*

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
        holder.number.text = (position + 1).toString()
        holder.accountId.text = item.accountIdentifier
        holder.description.text = item.description
        holder.bankId.text = item.bankIdentifier

        if (item.defaultAccount !== null && item.defaultAccount) {
            holder.defaultBadge.visibility = View.VISIBLE
        } else {
            holder.defaultBadge.visibility = View.INVISIBLE
        }

    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val number: TextView = mView.account_number
        val accountId: TextView = mView.accountIdentifier
        val description: TextView = mView.description
        val bankId: TextView = mView.bankIdentifier
        val defaultBadge: TextView = mView.defaultBadge
    }
}
