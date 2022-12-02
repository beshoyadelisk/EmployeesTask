package com.beshoy.employeestask.ui.add_employee

import com.beshoy.employeestask.data.entity.Employee

data class EmployeeUiState(
    val isLoading: Boolean = false,
    val employee: Employee? = null,
    val invalidName: Boolean = false,
    val invalidMail: Boolean = false,
    val messageRes: Int? = null
)
