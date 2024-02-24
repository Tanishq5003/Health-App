package com.example.healthapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [user::class], version = 2 , exportSchema = false)
abstract class AlarmDatabase: RoomDatabase() {
    abstract fun userDao(): userDao
    companion object{
        @Volatile
        private var INSTANCE: AlarmDatabase? = null

        val migration_1_2 : Migration = object : Migration(1,2){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE Alarm_Data ADD COLUMN status INT DEFAULT 0")
            }

        }

        fun getDatabase(context: Context): AlarmDatabase{
            val tempInstance = INSTANCE
            if (tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AlarmDatabase::class.java,
                    "alarm_database"
                ).addMigrations(migration_1_2)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}