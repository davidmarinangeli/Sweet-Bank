package com.davidm.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.davidm.utils.DashboardLocalMapper
import kotlinx.android.synthetic.main.purchase_list_item.view.*

class DashboardListAdapter : RecyclerView.Adapter<DashboardListAdapter.DashboardViewHolder>() {

    var data = listOf<DashboardLocalMapper.LocalPurchase>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class DashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mCounterParty: TextView = itemView.title
        val mDate: TextView = itemView.date
        val mAmount: TextView = itemView.amount
        val mSpendingCategory: CardView = itemView.spendingCategoryBox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.purchase_list_item, parent, false)
        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val item = data[position]
        holder.mCounterParty.text = item.counterPartyName
        holder.mSpendingCategory.setCardBackgroundColor(
            ResourcesCompat.getColor(holder.itemView.resources, item.spendingCategoryColor, null)
        )
        holder.mDate.text = item.date
        holder.mAmount.text = item.amount
    }
}