package com.beshoy.employeestask.di

import com.beshoy.employeestask.data.repository.EmployeeRepositoryImpl
import com.beshoy.employeestask.data.repository.SkillRepositoryImpl
import com.beshoy.employeestask.domain.repository.EmployeeRepository
import com.beshoy.employeestask.domain.repository.SkillRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModules {
    @Binds
    @Singleton
    abstract fun bindEmployeeRepository(repository: EmployeeRepositoryImpl): EmployeeRepository

    @Binds
    @Singleton
    abstract fun bindSkillsRepository(repository: SkillRepositoryImpl): SkillRepository
}