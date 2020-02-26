package com.davidm.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.davidm.utils.DashboardLocalMapper
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DashboardFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: DashboardViewModel
    lateinit var adapter: DashboardListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.purchase_list)
        val balanceCardTextView = view.findViewById<TextView>(R.id.balance_amount)
        val balanceCentsTextView = view.findViewById<TextView>(R.id.cents)
        val emptyListImageView = view.findViewById<ImageView>(R.id.empty_list_illustration)

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(DashboardViewModel::class.java)

        adapter = DashboardListAdapter()

        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        viewModel.accountBalanceLiveData.observe(viewLifecycleOwner, Observer {
            balanceCardTextView.text = it.amount
            balanceCentsTextView.text = it.amountCents
        })

        viewModel.purchasesLiveData.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                emptyListImageView.visibility = View.VISIBLE
            } else {
                adapter.data = it
            }
        })

    }
}
