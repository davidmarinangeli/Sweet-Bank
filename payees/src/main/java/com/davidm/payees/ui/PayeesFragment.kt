package com.davidm.payees.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davidm.payees.R

class PayeesFragment : Fragment() {

    private lateinit var viewModel: PayeesViewModel
    private lateinit var payeeListAdapter: PayeeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.payees_fragment, container, false)
    }


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val recyclerView = view.findViewById<RecyclerView>(R.id.payeeList)
//
//        payeeListAdapter = PayeeListAdapter()
//        viewModel =
//            ViewModelProvider(activity!!.viewModelStore, viewModelFactory).get(PayeesViewModel::class.java)
//
//        recyclerView.adapter = payeeListAdapter
//        recyclerView.addItemDecoration(
//            DividerItemDecoration(
//                recyclerView.context,
//                DividerItemDecoration.VERTICAL
//            )
//        )
//
//
//        viewModel.payeesLiveData.observe(viewLifecycleOwner, Observer {
//            payeeListAdapter.data = it
//        })
//
//    }
}