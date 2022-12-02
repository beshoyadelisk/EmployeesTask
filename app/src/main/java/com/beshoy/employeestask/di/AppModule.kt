package com.beshoy.employeestask.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.beshoy.employeestask.data.EmployeeDb
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
                        db.execSQL("INSERT INTO SKILL VALUES(1,'PHP')")
                        db.execSQL("INSERT INTO SKILL VALUES(2,'ASP.NET')")
                        db.execSQL("INSERT INTO SKILL VALUES(3,'iOS')")
                        db.execSQL("INSERT INTO SKILL VALUES(4,'Android')")

                    }
                }

            ).build()
        return employeeDb
    }

    @Provides
    @Singleton
    fun provideDispatcher(): CoroutineDispatcher = Dispatchers.IO
}