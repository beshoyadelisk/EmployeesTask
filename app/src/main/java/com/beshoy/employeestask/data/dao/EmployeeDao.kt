package com.beshoy.employeestask.data.dao

import androidx.room.*
import com.beshoy.employeestask.data.entity.Employee
import com.beshoy.employeestask.data.entity.EmployeeSkillCrossRef
import com.beshoy.employeestask.data.entity.EmployeeWithSkills

@Dao
interface EmployeeDao {
    @Transaction
    @Query("SELECT * FROM Employee")
    fun getEmployeesWithSkills(): List<EmployeeWithSkills>

    @Transaction
    @Insert
    fun insertEmployeeSkillCrossRef(employeeSkillCrossRef: EmployeeSkillCrossRef): Long

    @Transaction
    @Query("Delete from employeeskillcrossref where employeeId = :empId")
    fun deleteEmployeeSkillCrossRef(empId: Int)

    @Insert
    fun insert(employee: Employee): Long

    @Update
    fun update(employee: Employee): Int

    @Delete
    fun delete(employee: Employee)
}