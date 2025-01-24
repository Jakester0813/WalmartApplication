package com.jake.walmart.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jake.walmart.data.CASES
import com.jake.walmart.data.entities.Country
import com.jake.walmart.network.CountryResult
import com.jake.walmart.ui.usecases.CountriesUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val application: Application) : AndroidViewModel(application) {

    val countries: LiveData<State<List<Country>>> get() = _countries
    private val _countries = MutableLiveData<State<List<Country>>>()
    private var case : CASES = CASES.ORIGINAL_DATA //Change value to test different cases
    private val useCases = CountriesUseCases(application.baseContext)

    init {
        _countries.value = State.initial()
    }

    fun loadCountries() {
        _countries.value = State.loading()

        viewModelScope.launch {
            val state = withContext(Dispatchers.IO) {
                try {
                    val result = useCases.getCountries(case)
                    if (result is CountryResult.Success) {
                        if (result.data!!.isNotEmpty()) {
                            //There's data returned
                            State.success(result.data)
                        } else {
                            //There's no data returned
                            State.noData()
                        }
                    } else {
                        //Otherwise there might be some error from the network
                        State.error(result.error)
                    }
                } catch (e: Exception) {
                    //Something funky happened between getting data and returning data, parsing maybe?
                    State.error(e.message)
                }
            }
            _countries.value = state
        }
    }

    //This is for testing different UI and for Unit Test Cases
    fun changeCase(case: CASES) {
        this.case = case
    }

}