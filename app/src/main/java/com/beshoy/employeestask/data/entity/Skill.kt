package com.beshoy.employeestask.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Skill(
    @PrimaryKey
    val skillName: String
)