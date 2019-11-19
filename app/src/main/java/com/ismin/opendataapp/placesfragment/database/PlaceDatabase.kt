package com.ismin.opendataapp.placesfragment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlaceEntity::class], version = 1)
abstract class PlaceDatabase : RoomDatabase() {

    abstract fun getPlaceDAO(): PlaceDAO

    companion object {
        private var INSTANCE: PlaceDatabase? = null

        fun getAppDatabase(context: Context): PlaceDatabase {
            if (INSTANCE == null) {
                synchronized(PlaceDatabase::class) {
                    INSTANCE = Room
                        .databaseBuilder(
                            context.applicationContext,
                            PlaceDatabase::class.java,
                            "PlaceDB"
                        )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as PlaceDatabase
        }
    }
}
