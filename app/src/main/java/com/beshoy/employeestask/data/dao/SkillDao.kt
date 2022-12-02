package com.beshoy.employeestask.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.beshoy.employeestask.data.entity.Skill

@Dao
interface SkillDao {
    @Query("SELECT * FROM Skill")
    fun getAll(): List<Skill>

    @Insert
    fun insert(employee: Skill): Long

    @Delete
    fun delete(employee: Skill)
}