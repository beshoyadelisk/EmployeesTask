package com.beshoy.employeestask.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class EmployeeWithSkills(
    @Embedded val employee: Employee,
    @Relation(
        parentColumn = "employeeId",
        entityColumn = "skillId",
        associateBy = Junction(EmployeeSkillCrossRef::class)
    )
    val skills: List<Skill>
)
