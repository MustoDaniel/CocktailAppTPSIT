package com.example.cocktailapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cocktailapp.database.entities.SavedCocktail

@Dao
interface SavedCocktailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(savedCocktail: SavedCocktail)

    @Update
    suspend fun update(savedCocktail: SavedCocktail)

    @Delete
    suspend fun delete(savedCocktail: SavedCocktail)

    @Query("select s.idCocktail from cocktail c join savedcocktail s on c.idDrink = s.idCocktail ")
    suspend fun getSavedCocktails(): List<Int>
}