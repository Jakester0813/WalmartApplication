package com.jake.walmart.data.repositories

import com.jake.walmart.data.entities.Country
import com.jake.walmart.data.entities.CountryData
import com.jake.walmart.network.CountryResult

class CountriesCentralRepository(
    private val localRepository: CountriesLocalRepository,
    private val remoteRepository: CountriesRemoteRepository
) {
    suspend fun getDataFromDB(): CountryResult<List<Country>> {
        return try {
            CountryResult.Success(localRepository.getDataFromDB())
        } catch (e: Exception) {
            CountryResult.Error("Error fetching data from database")
        }
    }
    suspend fun getData(): CountryResult<List<CountryData>> {
        return try {
            CountryResult.Success(remoteRepository.getCountries())
        } catch (e: Exception) {
            CountryResult.Error("Error fetching data: ${e.message}")
        }
    }

    suspend fun getDataFromJson(name: String) : CountryResult<List<CountryData>> {
        return try {
            CountryResult.Success(localRepository.getDataFromJson(name))
        } catch (e: Exception) {
            CountryResult.Error("Error fetching data: ${e.message}")
        }
    }

    suspend fun saveData(countries: List<Country>) {
        localRepository.saveData(countries)
    }
}