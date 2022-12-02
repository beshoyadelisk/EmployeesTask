package com.beshoy.employeestask.data.repository

import com.beshoy.employeestask.data.dao.EmployeeDao
import com.beshoy.employeestask.data.entity.Employee
import com.beshoy.employeestask.data.entity.EmployeeWithSkills
import com.beshoy.employeestask.domain.repository.EmployeeRepository
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(private val dao: EmployeeDao) :
    EmployeeRepository {
    override suspend fun insertEmployee(employee: Employee): Long = dao.insert(employee)

    override suspend fun getEmployeesWithSKills(): List<EmployeeWithSkills> =
        dao.getEmployeesWithSkills()

    override suspend fun updateEmployee(employee: Employee): Int = dao.update(employee)

    override suspend fun deleteEmployee(employee: Employee) = dao.delete(employee)

}