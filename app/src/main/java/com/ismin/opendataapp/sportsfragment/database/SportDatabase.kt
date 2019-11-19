package com.ismin.opendataapp.sportsfragment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SportEntity::class], version = 1, exportSchema = false)
abstract class SportDatabase : RoomDatabase() {

    abstract fun getSportDAO(): SportDAO

    companion object {
        private var INSTANCE: SportDatabase? = null

        fun getAppDatabase(context: Context): SportDatabase {
            if (INSTANCE == null) {
                synchronized(SportDatabase::class) {
                    INSTANCE = Room
                        .databaseBuilder(
                            context.applicationContext,
                            SportDatabase::class.java,
                            "SportDB"
                        )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as SportDatabase
        }
    }
}
