package com.jake.walmart.ui.usecases

import android.content.Context
import com.jake.walmart.R
import com.jake.walmart.data.CASES
import com.jake.walmart.data.entities.Country
import com.jake.walmart.data.entities.CountryData
import com.jake.walmart.data.repositories.CountriesCentralRepository
import com.jake.walmart.data.repositories.CountriesLocalRepository
import com.jake.walmart.data.repositories.CountriesRemoteRepository
import com.jake.walmart.network.CountryResult
import com.jake.walmart.network.NetworkUtility

class CountriesUseCases(private val appContext: Context) {

    private val centralRepository = CountriesCentralRepository(
        CountriesLocalRepository(appContext),
        CountriesRemoteRepository()
    )

    suspend fun getCountries(case: CASES): CountryResult<List<Country>> {
        when (case) {
            CASES.ORIGINAL_DATA -> {
                val dbResult = centralRepository.getDataFromDB()
                val localData = dbResult.data
                return if (localData.isNullOrEmpty()) {
                    if (NetworkUtility.isNetworkAvailable(appContext)) {
                        parseResults(centralRepository.getData(), true)
                    } else {
                        CountryResult.Error(appContext.getString(R.string.no_internet_message))
                    }
                } else {
                    dbResult
                }
            }
            CASES.OK_DATA -> {
                return parseResults(centralRepository.getDataFromJson("some_blank_data.json"), false)
            }
            CASES.BAD_DATA -> {
                return parseResults(centralRepository.getDataFromJson("bad_data.json"), false)
            }
            CASES.NO_DATA -> {
                return parseResults(centralRepository.getDataFromJson("empty_data.json"), false)
            }
        }
    }

    private suspend fun parseResults(result: CountryResult<List<CountryData>>, saveToDB: Boolean) : CountryResult<List<Country>> {
        if (result is CountryResult.Success) {
            try {
                val data = result.data!!
                val parsedCountries = mutableListOf<Country>()
                for (country in data) {
                    parsedCountries.add(
                        Country(
                            country.name,
                            country.region,
                            country.code,
                            country.capital
                        )
                    )
                }
                if (saveToDB) centralRepository.saveData(parsedCountries)
                return CountryResult.Success(parsedCountries)
            } catch (e: Exception) {
                return CountryResult.Error("Oops, looks like we got some bad data. Let your favorite developer know!")
            }

        } else {
            return CountryResult.Error(result.error!!)
        }
    }
}