package com.beshoy.employeestask.di

import com.beshoy.employeestask.data.EmployeeDb
import com.beshoy.employeestask.data.dao.EmployeeDao
import com.beshoy.employeestask.data.dao.SkillDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Provides
    @Singleton
    fun provideEmployeeDao(db: EmployeeDb): EmployeeDao = db.employeeDao()

    @Provides
    @Singleton
    fun provideSkillsDao(db: EmployeeDb): SkillDao = db.skillDao()
}