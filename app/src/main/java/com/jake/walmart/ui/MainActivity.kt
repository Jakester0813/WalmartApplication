package com.jake.walmart.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jake.walmart.R
import com.jake.walmart.databinding.ActivityMainBinding
import com.jake.walmart.ui.adapters.CountriesAdapter
import com.jake.walmart.ui.util.getCurrentPosition
import com.jake.walmart.ui.viewmodels.MainViewModel
import com.jake.walmart.ui.viewmodels.Status

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel

    private lateinit var binding: ActivityMainBinding

    companion object {
        const val RECYCLER_POSITION = "recycler_position"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        setupUI(savedInstanceState)

        viewModel.countries.observeForever { state ->
            when (state.status) {
                Status.INITIAL -> {
                    binding.initialLayout.visibility = View.VISIBLE
                    binding.loadBtn.setOnClickListener {
                        viewModel.loadCountries()
                    }
                }
                Status.LOADING -> {
                    binding.initialLayout.visibility = View.GONE
                    binding.dataLayout.visibility = View.GONE

                    binding.loadingLayout.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.loadingLayout.visibility = View.GONE

                    binding.dataLayout.visibility = View.VISIBLE
                    binding.countryRecycler.layoutManager = LinearLayoutManager(
                        this,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    binding.countryRecycler.adapter = CountriesAdapter(state.data!!)
                }
                Status.ERROR -> {
                    binding.loadingLayout.visibility = View.GONE

                    binding.errorMessageText.text = state.message
                    binding.errorBtn.setOnClickListener {
                        viewModel.loadCountries()
                    }
                    binding.errorLayout.visibility = View.VISIBLE
                }
                Status.NO_DATA -> {
                    binding.loadingLayout.visibility = View.GONE

                    binding.noDataLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putInt(RECYCLER_POSITION, binding.countryRecycler.getCurrentPosition())
        super.onSaveInstanceState(outState, outPersistentState)
    }

    fun setupUI(savedInstanceState: Bundle?){
        binding.countryRecycler.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))

        if(savedInstanceState != null) {
            binding.countryRecycler.scrollToPosition(savedInstanceState.getInt(RECYCLER_POSITION, 0))
        }
    }
}