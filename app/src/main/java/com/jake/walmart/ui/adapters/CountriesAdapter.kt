package com.jake.walmart.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jake.walmart.R
import com.jake.walmart.data.entities.Country

class CountriesAdapter(private val countries: List<Country>) : RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {

    class CountriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.countryNameRegion)
        val codeTextView: TextView = itemView.findViewById(R.id.countryCode)
        val capitalTextView: TextView = itemView.findViewById(R.id.countryCapital)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        return CountriesViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.country_item_layout,
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        val country = countries[position]
        val context = holder.itemView.context
        if (country.region.isEmpty()) {
            holder.nameTextView.text = country.name
        } else {
            holder.nameTextView.text = context.getString(R.string.country_name, country.name, country.region)
        }
        holder.codeTextView.text = country.code
        holder.capitalTextView.text = country.capital
    }


}