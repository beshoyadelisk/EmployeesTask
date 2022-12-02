package com.beshoy.employeestask.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Employee(
    val fullName: String,
    val email: String? = null,
    val photo: String? = null
){
    @PrimaryKey(autoGenerate = true)
    var employeeId: Int = 0
}
