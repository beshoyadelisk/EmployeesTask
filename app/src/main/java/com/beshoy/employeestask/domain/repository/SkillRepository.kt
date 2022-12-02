package com.beshoy.employeestask.domain.repository

import com.beshoy.employeestask.data.entity.Skill
import com.beshoy.employeestask.util.Resource
import kotlinx.coroutines.flow.Flow

interface SkillRepository {
    suspend fun getSkills(): Flow<Resource<List<Skill>>>
    suspend fun insertSkills(skills: List<Skill>): Flow<Resource<List<Long>>>
    suspend fun deleteSkill(skill: Skill)
}