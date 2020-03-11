package com.davidm.payees.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.davidm.payees.R
import com.davidm.payees.utils.PayeesLocalMapper
import kotlinx.android.synthetic.main.payee_item.view.*

class PayeeListAdapter : RecyclerView.Adapter<PayeeListAdapter.PayeeListViewHolder>() {

    var data = listOf<PayeesLocalMapper.LocalPayee>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class PayeeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val payeeName: TextView = itemView.payeeName
        val firstLastName: TextView = itemView.firstAndLastName
        val accountsNumber: TextView = itemView.accountNumberText
        val initials : TextView = itemView.initials
        val parent: ConstraintLayout = itemView.payeeItemParentView
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayeeListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.payee_item, parent, false)
        return PayeeListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PayeeListViewHolder, position: Int) {
        val item = data[position]
        holder.payeeName.text = item.payeeName
        holder.firstLastName.text = item.firstAndLastName
        holder.accountsNumber.text = item.numberOfAccounts
        holder.initials.text = item.initials
        holder.parent.setOnClickListener {
            val intent = Intent(holder.itemView.context, PayeeProfileActivity::class.java)

            intent.putExtra("profileData", item)
            startActivity(holder.itemView.context, intent, null)

        }
    }
}