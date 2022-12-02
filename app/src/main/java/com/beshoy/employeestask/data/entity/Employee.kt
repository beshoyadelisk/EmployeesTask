package com.beshoy.employeestask.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    @PrimaryKey
    val fullName: String,
    val email: String? = null,
    val photo: String? = null,
    val skills: MutableList<String> = mutableListOf()
)
