package com.beshoy.employeestask.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey(autoGenerate = true)
    val employeeId: Int,
    val fullName: String,
    val email: String? = null,
    val photo: String? = null
)
