package com.jake.walmart.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class Country (
    @PrimaryKey val name: String,
    val region: String,
    val code: String,
    val capital: String
)