package com.davidm.payees.ui

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.davidm.payees.R
import com.davidm.payees_profile.utils.PayeeProfileInfoConverter
import kotlinx.android.synthetic.main.payee_profile_info_item.view.*

class PayeeProfileInfoAdapter(
    val data: List<PayeeProfileInfoConverter.PayeeProfileInfo>
) : RecyclerView.Adapter<PayeeProfileInfoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.payee_profile_info_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.text.capitalize()
        holder.imageView.setImageResource(item.icon)

    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val textView: TextView = mView.payeeInfoText
        val imageView: ImageView = mView.payeeInfoIcon
    }
}
