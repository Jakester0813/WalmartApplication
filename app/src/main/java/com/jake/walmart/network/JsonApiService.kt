package com.jake.walmart.network

import com.jake.walmart.data.entities.CountryData
import retrofit2.http.GET

interface JsonApiService {

    @GET("peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/countries.json")
    suspend fun getJsonData(): List<CountryData>
}