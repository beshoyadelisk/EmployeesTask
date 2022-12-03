package com.beshoy.employeestask.data.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmployeeWithSkills(
    @Embedded val employee: Employee,
    @Relation(
        parentColumn = "employeeId",
        entityColumn = "skillId",
        associateBy = Junction(EmployeeSkillCrossRef::class)
    )
    var skills: List<Skill>
): Parcelable
