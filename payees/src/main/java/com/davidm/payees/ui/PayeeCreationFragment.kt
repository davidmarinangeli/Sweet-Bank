package com.davidm.payees.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.davidm.payees.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PayeeCreationFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: PayeesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.payee_creation_fragment, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        val dialog: BottomSheetDialog =
            super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//
//        viewModel =
//            ViewModelProvider(
//                activity!!.viewModelStore,
//                viewModelFactory
//            ).get(PayeesViewModel::class.java)
//
//
//        val map: Map<TextInputLayout, TextInputEditText> =
//            mapOf(
//                Pair(payeeNameInputLayout, payeeNameEditText),
//                Pair(businessNameInputLayout, businessNameEditText)
//            )
//
//        createButton.setOnClickListener {
//            searchAndSetErrorsOnForm(map)
//            if (isFormValid(map)) {
//
//                // we create a payee with the form's texts and some default values
//                val payeeToBeCreated = Payee(
//                    listOf(defaultAccount),
//                    businessNameEditText.text.toString(),
//                    "1990-01-01",
//                    firstNameEditText.text.toString(),
//                    lastNameEditText.text.toString(),
//                    null,
//                    payeeNameEditText.text.toString(),
//                    PayeeType.BUSINESS,
//                    "0",
//                    phoneNumberEditText.text.toString()
//                )
//
//                viewModel.createPayee(payeeToBeCreated)
//
//                showSnackBarAndRefreshList()
//            }
//
//        }
//
    }
//
//    private fun showSnackBarAndRefreshList() {
//        viewModel.creationResponseLiveData.observe(viewLifecycleOwner, Observer {
//            if ((activity?.window?.decorView?.rootView !== null)) {
//                if (it.errors.isNullOrEmpty() && it.success) {
//                    Snackbar.make(
//                        activity?.window?.decorView?.rootView!!,
//                        "Success! The Payee has been created",
//                        Snackbar.LENGTH_LONG
//                    ).show()
//
//                    viewModel.getPayees()
//
//                } else {
//                    Snackbar.make(
//                        activity?.window?.decorView?.rootView!!,
//                        "There was an error: ${it.errors?.get(0)?.error} , ${it.errors?.get(
//                            0
//                        )?.error_description}",
//                        Snackbar.LENGTH_LONG
//                    ).show()
//                }
//            }
//            dismiss()
//        })
//    }
}
