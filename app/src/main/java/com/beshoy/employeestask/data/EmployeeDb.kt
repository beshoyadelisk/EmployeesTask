package com.beshoy.employeestask.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.beshoy.employeestask.data.converters.ListConverter
import com.beshoy.employeestask.data.dao.EmployeeDao
import com.beshoy.employeestask.data.entity.Employee


@Database(
    entities = [Employee::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(ListConverter::class)
abstract class EmployeeDb : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}