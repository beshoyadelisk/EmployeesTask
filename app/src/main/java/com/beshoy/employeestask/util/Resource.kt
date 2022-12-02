package com.beshoy.employeestask.util

import androidx.annotation.StringRes

sealed class Resource<T>(val data: T? = null, @StringRes val message: Int? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(@StringRes message: Int, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(val isLoading: Boolean = true) : Resource<T>()
}