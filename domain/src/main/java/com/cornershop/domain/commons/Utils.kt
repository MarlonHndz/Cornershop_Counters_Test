package com.cornershop.domain.commons

import com.cornershop.domain.repositories.serviceHandler.ServiceHandler
import com.cornershop.domain.repositories.serviceHandler.ServiceStatus

object Utils {
    const val MY_PREFERENCES = "my_preferences"
    const val PREFERENCE_SHOW_WELCOME = "preference_show_welcome"


    const val EMPTY_STRING = ""
    const val COMMA_SEPARATOR = ", "
    const val NEW_LINE = "\n"

    fun ServiceHandler.isServiceGlobalError(): Boolean {
        return this.status == ServiceStatus.ERROR_SERVICE || this.status == ServiceStatus.ERROR_TIMEOUT || this.status == ServiceStatus.ERROR_UNKNOWN_HOST
    }
}
