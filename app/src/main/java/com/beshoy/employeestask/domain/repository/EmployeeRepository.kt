package com.beshoy.employeestask.domain.repository

import com.beshoy.employeestask.data.entity.Employee
import com.beshoy.employeestask.data.entity.EmployeeSkillCrossRef
import com.beshoy.employeestask.data.entity.EmployeeWithSkills
import com.beshoy.employeestask.util.Resource
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    suspend fun insertEmployeeWithSkill(employeeWithSkills: EmployeeWithSkills): Flow<Resource<Long>>
    suspend fun getEmployeesWithSKills(): Flow<Resource<List<EmployeeWithSkills>>>
    suspend fun updateEmployee(employee: EmployeeWithSkills): Flow<Resource<Int>>
    suspend fun deleteEmployee(employee: Employee)
}