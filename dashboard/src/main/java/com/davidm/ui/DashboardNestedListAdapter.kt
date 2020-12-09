package com.davidm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.davidm.utils.DashboardLocalMapper
import kotlinx.android.synthetic.main.purchase_list_item.view.*

class DashboardNestedListAdapter : RecyclerView.Adapter<DashboardNestedListAdapter.DashboardNestedListViewHolder>() {

    var data = listOf<DashboardLocalMapper.LocalPurchase>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class DashboardNestedListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mCounterParty: TextView = itemView.transactionTitle
        val mDate: TextView = itemView.shimmerDate
        val mAmount: TextView = itemView.shimmerAmount
        val mSpendingCategory: ImageView = itemView.spendingCategoryBox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardNestedListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.purchase_list_item, parent, false)
        return DashboardNestedListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DashboardNestedListViewHolder, position: Int) {
        val item = data[position]
        holder.mCounterParty.text = item.counterPartyName
        holder.mSpendingCategory.setImageResource(item.spendingCategoryIcon)
        holder.mDate.text = item.date
        holder.mAmount.text = item.amount
        holder.mAmount.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                item.amountColor
            )
        )


    }
}