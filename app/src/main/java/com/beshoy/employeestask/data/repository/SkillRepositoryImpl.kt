package com.beshoy.employeestask.data.repository

import com.beshoy.employeestask.data.dao.SkillDao
import com.beshoy.employeestask.data.entity.Skill
import com.beshoy.employeestask.domain.repository.SkillRepository
import javax.inject.Inject

class SkillRepositoryImpl  @Inject constructor(private val dao: SkillDao) : SkillRepository {
    override suspend fun insertSkills(skills: List<Skill>): List<Long> = dao.insert(skills)

    override suspend fun deleteSkill(skill: Skill) = dao.delete(skill)
}