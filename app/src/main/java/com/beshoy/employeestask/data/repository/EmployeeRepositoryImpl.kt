package com.beshoy.employeestask.data.repository

import com.beshoy.employeestask.data.dao.EmployeeDao
import com.beshoy.employeestask.data.entity.Employee
import com.beshoy.employeestask.data.entity.EmployeeSkillCrossRef
import com.beshoy.employeestask.data.entity.EmployeeWithSkills
import com.beshoy.employeestask.domain.repository.EmployeeRepository
import com.beshoy.employeestask.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(private val dao: EmployeeDao) :
    EmployeeRepository {
    override suspend fun insertEmployeeWithSkill(employeeWithSkills: EmployeeWithSkills): Flow<Resource<Long>> =
        flow {
            val id = dao.insert(employeeWithSkills.employee)
            employeeWithSkills.skills.forEach {
                dao.insertEmployeeSkillCrossRef(EmployeeSkillCrossRef(id.toInt(), it.skillId))
            }
            emit(Resource.Success(employeeWithSkills.skills.size.toLong()))
        }

    override suspend fun getEmployeesWithSKills(): Flow<Resource<List<EmployeeWithSkills>>> = flow {
        val employeesWithSkills = dao.getEmployeesWithSkills()
        emit(Resource.Success(employeesWithSkills))
    }

    override suspend fun updateEmployee(employee: Employee): Flow<Resource<Int>> = flow {
        val result = dao.update(employee)
        emit(Resource.Success(result))
    }

    override suspend fun deleteEmployee(employee: Employee) = dao.delete(employee)

}