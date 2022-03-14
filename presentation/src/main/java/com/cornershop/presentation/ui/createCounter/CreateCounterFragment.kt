package com.cornershop.presentation.ui.createCounter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.cornershop.domain.commons.Utils.COMMA_SEPARATOR
import com.cornershop.domain.commons.Utils.EMPTY_STRING
import com.cornershop.domain.commons.Utils.NEW_LINE
import com.cornershop.domain.repositories.serviceHandler.ServiceHandler
import com.cornershop.domain.repositories.serviceHandler.ServiceStatus
import com.cornershop.presentation.R
import com.cornershop.presentation.databinding.CreateCounterFragmentBinding
import com.cornershop.presentation.ui.baseViews.BaseFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class CreateCounterFragment : BaseFragment() {

    private lateinit var binding: CreateCounterFragmentBinding

    private val createCounterViewModel: CreateCounterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateCounterFragmentBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        setUpViews()
    }

    private fun setUpObservers() {
        // Another way to handle Service Status
        with(createCounterViewModel) {
            serviceStatusHandlerLiveData.observe(viewLifecycleOwner) { serviceHandler ->
                onServiceStatusChanged(serviceHandler) {}
            }
        }
    }

    override fun onServiceStatusChanged(
        serviceHandler: ServiceHandler,
        onRetry: () -> Unit
    ) {
        when (serviceHandler.status) {
            ServiceStatus.LOADING -> {
                showLoading(true)
            }
            ServiceStatus.ERROR_SERVICE,
            ServiceStatus.ERROR_TIMEOUT,
            ServiceStatus.ERROR_UNKNOWN_HOST,
            ServiceStatus.ERROR_NO_INTERNET_CONNECTION -> {
                showLoading(false)
                showErrorAlert()
            }
            ServiceStatus.SUCCESS -> {
                activity?.onBackPressed()
            }
        }
    }

    private fun setUpViews() {
        binding.btnClose.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.txtBtnSave.setOnClickListener {
            val name = binding.edTxtCounterName.text
            if (!name.isNullOrEmpty()) {
                createCounterViewModel.saveCounter(name.toString())
                binding.txtInputCounterName.helperText = null
                hideSoftKeyboard()
            } else {
                binding.txtInputCounterName.helperText = helperText()
            }
        }
        binding.edTxtCounterName.requestFocus()
        showSoftKeyboard(binding.edTxtCounterName)
    }

    private fun helperText(): String {
        val helperMsg =
            context?.let { resources.getString(R.string.create_counter_disclaimer) } ?: EMPTY_STRING
        val helperOptions =
            context?.let {
                resources.getStringArray(R.array.drinks_array).joinToString(COMMA_SEPARATOR)
            } ?: EMPTY_STRING

        return helperMsg + NEW_LINE + helperOptions
    }

    private fun showLoading(isVisible: Boolean) {
        with(binding) {
            pbSaveLoading.isVisible = isVisible
            txtBtnSave.isVisible = !isVisible
        }
    }

    private fun showErrorAlert() {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(it.getString(R.string.error_creating_counter_title))
                .setMessage(it.getString(R.string.connection_error_description))
                .setPositiveButton(it.getString(R.string.ok)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}
