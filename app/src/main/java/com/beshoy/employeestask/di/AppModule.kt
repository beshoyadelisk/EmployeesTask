package com.beshoy.employeestask.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.beshoy.employeestask.data.EmployeeDb
import com.beshoy.employeestask.data.entity.Skill
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private lateinit var employeeDb: EmployeeDb
    @Provides
    @Singleton
    fun provideEditaDatabase(app: Application): EmployeeDb {
        employeeDb = Room.databaseBuilder(app, EmployeeDb::class.java, "EmployeeDb.db")
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .addCallback(
                object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        employeeDb.skillDao().insert(listOf(
                            Skill(1,"PHP"),
                            Skill(2,"ASP.NET"),
                            Skill(3,"iOS"),
                            Skill(4,"Android")
                        ))
                    }
                }

            ).build()
        return employeeDb
    }

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
}