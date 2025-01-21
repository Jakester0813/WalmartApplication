package com.jake.walmart.network


sealed class CountryResult<T> (val data:T? = null, val error:String? = null) {
    class Success<T>(data: T?) : CountryResult<T>(data)
    class Error<T>(e: String, data: T? = null) : CountryResult<T> (data, e)
}