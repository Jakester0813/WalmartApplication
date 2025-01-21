package com.jake.walmart.data.repositories

import com.jake.walmart.data.entities.Country
import com.jake.walmart.data.entities.CountryData

interface LocalRepository {
    suspend fun getDataFromDB() : List<Country>
    suspend fun getDataFromJson(fileName: String) : List<CountryData>
    suspend fun saveData(data: List<Country>)
}

interface RemoteRepository {
    suspend fun getCountries(): List<Any>
}