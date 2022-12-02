package com.beshoy.employeestask.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Skill(
    @PrimaryKey(autoGenerate = true)
    val skillId: Int,
    val skillName: String
){
    override fun toString(): String {
        return skillName
    }
}
