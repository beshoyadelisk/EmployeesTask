package com.beshoy.employeestask.domain.repository

import com.beshoy.employeestask.data.entity.Skill

interface SkillRepository {
    suspend fun insertSkills(skills: List<Skill>): List<Long>
    suspend fun deleteSkill(skill: Skill)
}