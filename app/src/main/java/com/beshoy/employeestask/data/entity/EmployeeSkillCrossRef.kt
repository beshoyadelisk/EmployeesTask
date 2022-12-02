package com.beshoy.employeestask.data.entity

import androidx.room.Entity

@Entity(primaryKeys = ["employeeId", "skillId"])
data class EmployeeSkillCrossRef(
    val employeeId: Int,
    val skillId: Int
)
