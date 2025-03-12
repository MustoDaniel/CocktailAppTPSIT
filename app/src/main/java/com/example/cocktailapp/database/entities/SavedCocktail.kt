package com.example.cocktailapp.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["idCocktail"],
    foreignKeys = [
        ForeignKey(
            entity = Cocktail::class,
            parentColumns = ["idDrink"],
            childColumns = ["idCocktail"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class SavedCocktail(
    val idCocktail: Int,
)
