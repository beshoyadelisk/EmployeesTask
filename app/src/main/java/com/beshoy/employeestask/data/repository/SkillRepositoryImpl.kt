package com.beshoy.employeestask.data.repository

import com.beshoy.employeestask.R
import com.beshoy.employeestask.data.dao.SkillDao
import com.beshoy.employeestask.data.entity.Skill
import com.beshoy.employeestask.domain.repository.SkillRepository
import com.beshoy.employeestask.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SkillRepositoryImpl @Inject constructor(private val dao: SkillDao) : SkillRepository {
    override suspend fun getSkills(): Flow<Resource<List<Skill>>> = flow {
        val skills = dao.getAll()
        emit(Resource.Success(skills))
    }

    override suspend fun insertSkills(skills: List<Skill>): Flow<Resource<List<Long>>> = flow {
        val result = dao.insert(skills)
        if (result.isNotEmpty()) {
            emit(Resource.Success(result))
        } else {
            emit(Resource.Error(R.string.failed_to_insert_skills))
        }
    }

    override suspend fun deleteSkill(skill: Skill) = dao.delete(skill)
}