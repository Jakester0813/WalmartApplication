package com.jake.walmart.data.repositories

import com.google.gson.Gson
import com.jake.walmart.data.entities.Country
import com.jake.walmart.data.entities.CountryData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class CountriesRemoteRepository : RemoteRepository{

    val url = URL("https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json")

    override suspend fun getCountries(): List<CountryData> {
            return try {
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val json = inputStream.bufferedReader().use { it.readText() }
                    inputStream.close()

                    val countriesResult = Gson().fromJson(json, Array<CountryData>::class.java)
                    if (countriesResult.isEmpty()) {
                        emptyList()
                    } else {
                        countriesResult.toList()
                    }
                } else {
                   throw Exception("HTTP error: ${connection.responseCode} - ${connection.responseMessage}")
                }
            } catch (e: Exception){
                throw Exception("Error fetching remote JSON: ${e.message}", e)
            }
    }


}