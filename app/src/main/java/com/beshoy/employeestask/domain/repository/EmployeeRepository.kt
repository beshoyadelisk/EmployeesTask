package com.beshoy.employeestask.domain.repository

import com.beshoy.employeestask.data.entity.Employee

interface EmployeeRepository {
   suspend fun insertEmployee(employee: Employee):Long
   suspend fun getEmployees(): List<Employee>
   suspend fun updateEmployee(employee: Employee):Int
   suspend fun deleteEmployee(employee: Employee)
}