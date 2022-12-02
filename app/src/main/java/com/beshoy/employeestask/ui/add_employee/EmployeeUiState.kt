package com.beshoy.employeestask.ui.add_employee

import com.beshoy.employeestask.data.entity.EmployeeWithSkills
import com.beshoy.employeestask.data.entity.Skill

data class EmployeeUiState(
    val isLoading: Boolean = false,
    val employeeWithSkills: EmployeeWithSkills? = null,
    val skills: MutableList<Skill> = mutableListOf(),
    val invalidMail: Boolean = false,
    val messageRes: Int? = null
)
