package com.beshoy.employeestask.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.beshoy.employeestask.data.converters.ListConverter
import com.beshoy.employeestask.data.dao.EmployeeDao
import com.beshoy.employeestask.data.dao.SkillDao
import com.beshoy.employeestask.data.entity.Employee
import com.beshoy.employeestask.data.entity.EmployeeSkillCrossRef
import com.beshoy.employeestask.data.entity.Skill


@Database(
    entities = [
        Employee::class,
        Skill::class,
        EmployeeSkillCrossRef::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(ListConverter::class)
abstract class EmployeeDb : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
    abstract fun skillDao(): SkillDao
}