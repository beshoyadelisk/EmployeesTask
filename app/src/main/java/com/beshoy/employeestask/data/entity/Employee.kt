package com.beshoy.employeestask.data.entity

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Employee(
    var fullName: String,
    var email: String? = null,
    @IgnoredOnParcel
    var photo: Bitmap? = null
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var employeeId: Int = 0
}
