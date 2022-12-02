package com.beshoy.employeestask.util

import android.util.Patterns

object TextUtil {
     fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}