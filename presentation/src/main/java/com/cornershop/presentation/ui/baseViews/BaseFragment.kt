package com.cornershop.presentation.ui.baseViews

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
}
