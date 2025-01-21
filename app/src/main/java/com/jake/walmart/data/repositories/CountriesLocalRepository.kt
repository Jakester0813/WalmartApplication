package com.jake.walmart.data.repositories

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jake.walmart.data.database.CountryDatabase
import com.jake.walmart.data.entities.Country
import com.jake.walmart.data.entities.CountryData
import org.json.JSONException
import java.io.File

class CountriesLocalRepository (val appContext: Context) : LocalRepository {

    private val database = CountryDatabase.getInstance(appContext)

    override suspend fun getDataFromDB(): List<Country> {
        return database.countryDao().getCountries()
    }

    override suspend fun getDataFromJson(fileName: String): List<CountryData> {
        try {
            val json = appContext.assets.open(fileName).bufferedReader().use { it.readText() }
            val list = Gson().fromJson(json, Array<CountryData>::class.java).toList()
            return list
        } catch (e: JSONException) {
            Log.e("JSON PARSING ERROR", "Error while parsing $fileName")
            return emptyList()
        }
    }

    override suspend fun saveData(data: List<Country>) {
        return database.countryDao().insertData(data)
    }

}