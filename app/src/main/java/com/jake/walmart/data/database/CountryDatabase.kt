package com.jake.walmart.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jake.walmart.data.dao.CountryDao
import com.jake.walmart.data.entities.Country

@Database(entities = [Country::class], version = 1)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao

    companion object {
        private const val DB_NAME = "country-db"

        @Volatile
        private var instance: CountryDatabase? = null

        fun getInstance(appContext: Context): CountryDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(appContext).also { instance = it }
            }
        }

        private fun buildDatabase(appContext: Context): CountryDatabase {
            return Room.databaseBuilder(
                appContext,
                CountryDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}