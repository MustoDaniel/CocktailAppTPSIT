package com.example.cocktailapp.data

import com.example.cocktailapp.database.entities.Cocktail

data class CocktailResponse(
    val drinks : List<Cocktail>?
)
