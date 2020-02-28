package com.davidm.payees.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.davidm.payees.R
import com.davidm.payees.entities.Payee
import com.davidm.payees.entities.PayeeType
import com.davidm.payees.entities.defaultAccount
import com.davidm.payees.utils.isFormValid
import com.davidm.payees.utils.searchAndSetErrorsOnForm
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.payee_creation_fragment.*
import javax.inject.Inject

class PayeeCreationFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PayeesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.payee_creation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, viewModelFactory).get(PayeesViewModel::class.java)


        val map: Map<TextInputLayout, TextInputEditText> =
            mapOf(
                Pair(payeeNameInputLayout, payeeNameEditText),
                Pair(businessNameInputLayout, businessNameEditText)
            )

        createButton.setOnClickListener {
            searchAndSetErrorsOnForm(map)
            if (isFormValid(map)) {

                // we create a payee with the form's texts and some default values
                val payeeToBeCreated = Payee(
                    listOf(defaultAccount),
                    businessNameEditText.text.toString(),
                    "1990-01-01",
                    firstNameEditText.text.toString(),
                    lastNameEditText.text.toString(),
                    null,
                    payeeNameEditText.text.toString(),
                    PayeeType.BUSINESS,
                    "0",
                    phoneNumberEditText.text.toString())

                viewModel.createPayee(payeeToBeCreated)
                viewModel.creationResponseLiveData.observe(viewLifecycleOwner, Observer {
                    dismiss()
                    if ((activity?.window?.decorView?.rootView !== null)) {
                        if (it.errors.isNullOrEmpty() && it.success) {
                            Snackbar.make(
                                activity?.window?.decorView?.rootView!!,
                                "Success! The Payee has been created",
                                Snackbar.LENGTH_LONG
                            ).show()
                        } else {
                            Snackbar.make(
                                activity?.window?.decorView?.rootView!!,
                                "There was an error: ${it.errors?.get(0)?.error} , ${it.errors?.get(
                                    0
                                )?.error_description}",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                })
            }

        }

    }
}