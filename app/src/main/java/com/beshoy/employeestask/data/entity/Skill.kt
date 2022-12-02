package com.beshoy.employeestask.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Skill(
    @PrimaryKey(autoGenerate = true)
    val skillId: Int,
    val skillName: String
): Parcelable {
    override fun toString(): String {
        return skillName
    }
}
