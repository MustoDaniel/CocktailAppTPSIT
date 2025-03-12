package com.example.cocktailapp.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = ["name"], unique = true)]
)
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String
)