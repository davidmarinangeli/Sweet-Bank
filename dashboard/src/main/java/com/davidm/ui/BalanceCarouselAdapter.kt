package com.davidm.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.davidm.utils.DashboardLocalMapper
import kotlinx.android.synthetic.main.balance_card_item.view.*


class BalanceCarouselAdapter(
) : RecyclerView.Adapter<BalanceCarouselAdapter.ViewHolder>() {

    var data = listOf<DashboardLocalMapper.LocalAccountBalance>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.balance_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.mIdView.text = item.amount
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.balance_amount
    }
}
