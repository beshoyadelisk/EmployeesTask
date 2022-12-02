package com.beshoy.employeestask.ui.add_employee

import com.beshoy.employeestask.data.entity.Employee

data class EmployeeUiState(
    val isLoading: Boolean = false,
    val employee: Employee? = null,
    val messageRes: Int? = null
)
