package com.perennial.androidassignmentweatherapp.utils

import android.text.TextUtils
import androidx.core.util.PatternsCompat

const val MIN_PASSWORD_LEN = 5
class FormValidationUtils {
    companion object {
        fun validateEmail(email: String): Boolean {
            return !TextUtils.isEmpty(email) && PatternsCompat.EMAIL_ADDRESS.matcher(email)
                .matches();
        }
    }
}