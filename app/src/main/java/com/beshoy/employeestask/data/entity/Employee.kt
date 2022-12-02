package com.beshoy.employeestask.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Employee(
    val fullName: String,
    val email: String? = null,
    val photo: String? = null
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var employeeId: Int = 0
}
