package com.beshoy.employeestask.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["employeeId", "skillId"])
data class EmployeeSkillCrossRef(
    val employeeId: Int,
    @ColumnInfo(index = true)
    val skillId: Int
)
