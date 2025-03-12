package com.example.cocktailapp.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["idCocktail", "idIngredient"],
    foreignKeys = [
        ForeignKey(
            entity = Cocktail::class,
            parentColumns = ["idDrink"],
            childColumns = ["idCocktail"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = ["id"],
            childColumns = ["idIngredient"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Cocktail_Ingredient(
    val idCocktail: Int,
    val idIngredient: Int
)
