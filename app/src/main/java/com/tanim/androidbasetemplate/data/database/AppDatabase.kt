package com.tanim.androidbasetemplate.data.database

import android.content.Context
import javax.inject.Singleton
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room

@Singleton
@Database(entities = [], version = 40)
abstract class AppDatabase : RoomDatabase() {

    companion object{
        private var database: AppDatabase? = null

        fun getDatabase(): AppDatabase? {

            if (database == null) {
                synchronized(AppDatabase::class.java) {
                    if (database == null) database = Room.databaseBuilder(
                        App.getContext(),
                        AppDatabase::class.java, "app_database"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return database
        }

        fun createDatabase(context: Context?): AppDatabase? {
            if (database == null) {
                synchronized(AppDatabase::class.java) {
                    if (database == null) database = Room.databaseBuilder(
                        context!!,
                        AppDatabase::class.java, "app_database"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return database
        }
    }




}