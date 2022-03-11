package com.cornershop.presentation.ui.baseViews

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.cornershop.domain.repositories.serviceHandler.ServiceHandler
import com.cornershop.domain.repositories.serviceHandler.ServiceStatus

abstract class BaseFragment : Fragment() {

    open fun onServiceStatusChanged(
        serviceHandler: ServiceHandler,
        onRetry: () -> Unit
    ) {
        when (serviceHandler.status) {
            ServiceStatus.LOADING -> {
                // loading
            }
            ServiceStatus.ERROR_TIMEOUT -> {
                // timeout
            }
            ServiceStatus.ERROR_NO_INTERNET_CONNECTION, ServiceStatus.ERROR_UNKNOWN_HOST -> {
                // offline
            }
            ServiceStatus.ERROR_SERVICE -> {
                // error service
            }
            ServiceStatus.SUCCESS -> {
                // success
            }
        }
    }

    fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
