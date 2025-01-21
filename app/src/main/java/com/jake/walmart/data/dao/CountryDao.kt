package com.jake.walmart.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jake.walmart.data.entities.Country

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(country: List<Country>)

    @Query("SELECT * FROM country order by name asc")
    suspend fun getCountries(): List<Country>
}