package com.beshoy.employeestask.data.repository

import com.beshoy.employeestask.data.dao.EmployeeDao
import com.beshoy.employeestask.data.entity.Employee
import com.beshoy.employeestask.data.entity.EmployeeWithSkills
import com.beshoy.employeestask.domain.repository.EmployeeRepository
import com.beshoy.employeestask.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(private val dao: EmployeeDao) :
    EmployeeRepository {
    override suspend fun insertEmployee(employee: Employee): Flow<Resource<Long>> = flow {
        emit(Resource.Success(dao.insert(employee)))
    }

    override suspend fun getEmployeesWithSKills(): Flow<Resource<List<EmployeeWithSkills>>> = flow {
        val employeesWithSkills = dao.getEmployeesWithSkills()
        emit(Resource.Success(employeesWithSkills))
    }

    override suspend fun updateEmployee(employee: Employee): Flow<Resource<Int>> =flow {
       val result =  dao.update(employee)
        emit(Resource.Success(result))
    }

    override suspend fun deleteEmployee(employee: Employee) = dao.delete(employee)

}