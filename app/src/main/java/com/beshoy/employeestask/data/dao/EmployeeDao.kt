package com.beshoy.employeestask.data.dao

import androidx.room.*
import com.beshoy.employeestask.data.entity.Employee

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM Employee")
    fun getAll(): List<Employee>

    @Insert
    fun insert(employee: Employee): Long

    @Update
    fun update(employee: Employee): Int

    @Delete
    fun delete(employee: Employee)
}