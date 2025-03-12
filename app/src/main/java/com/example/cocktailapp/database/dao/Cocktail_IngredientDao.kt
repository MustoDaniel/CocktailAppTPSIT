package com.example.cocktailapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cocktailapp.database.entities.Cocktail_Ingredient

@Dao
interface Cocktail_IngredientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cocktailIngredient: Cocktail_Ingredient)

    @Query("select i.name from ingredient i " +
            "join cocktail_ingredient ci on i.id = ci.idIngredient " +
            "join cocktail c on c.idDrink = ci.idCocktail " +
            "where c.strDrink = :cocktailName")
    fun getIngredientsByCocktailName(cocktailName: String): List<String>
}