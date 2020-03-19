package com.davidm.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.android.synthetic.main.empty_list_item.view.*
import kotlinx.android.synthetic.main.parent_list_item.view.*
import kotlinx.android.synthetic.main.shimmer_layout.view.*
import java.text.DateFormatSymbols


class DashboardParentListAdapter(
    val data: List<ParentListItem>,
    val context: Context,
    var loading: Boolean
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(
    ) {


    inner class DashboardLoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shimmer: ShimmerFrameLayout = itemView.shimmer_view_container
    }

    inner class DashboardParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val list: RecyclerView = itemView.purchase_list
    }

    inner class DashboardEmptyListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val emptyListTextView: TextView = itemView.no_results
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 ->
                DashboardLoadingViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shimmer_layout, parent, false)
                )
            1 -> DashboardEmptyListViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.empty_list_item, parent, false)
            )
            else -> DashboardParentViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.parent_list_item, parent, false)
            )
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]

        when (holder) {
            is DashboardParentViewHolder -> holder.list.adapter = item.listAdapter
            is DashboardEmptyListViewHolder -> holder.emptyListTextView.text =
                context.resources.getString(
                    R.string.no_result_string,
                    DateFormatSymbols().months[item.month]
                )
            is DashboardLoadingViewHolder -> holder.shimmer.run { stopShimmer() }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return when {
            loading -> 0
            data[position].listAdapter.data.isEmpty() -> 1
            else -> 2
        }
    }

    data class ParentListItem(
        val month: Int,
        var listAdapter: DashboardNestedListAdapter
    )
}