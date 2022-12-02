package com.beshoy.employeestask.domain.repository

import com.beshoy.employeestask.data.entity.Employee
import com.beshoy.employeestask.data.entity.EmployeeWithSkills

interface EmployeeRepository {
   suspend fun insertEmployee(employee: Employee):Long
   suspend fun getEmployeesWithSKills(): List<EmployeeWithSkills>
   suspend fun updateEmployee(employee: Employee):Int
   suspend fun deleteEmployee(employee: Employee)
}