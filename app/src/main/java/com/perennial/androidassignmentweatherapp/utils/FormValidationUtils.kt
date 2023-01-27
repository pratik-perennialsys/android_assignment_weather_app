package com.perennial.androidassignmentweatherapp.utils

import android.text.TextUtils

class FormValidationUtils {
    companion object {
        fun validateEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
        }
    }
}