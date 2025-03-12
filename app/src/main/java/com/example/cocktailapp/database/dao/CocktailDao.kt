package com.example.cocktailapp.database.dao

import com.example.cocktailapp.database.entities.Cocktail
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CocktailDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cocktail: Cocktail)

    @Query("select * from cocktail where idDrink = :id")
    suspend fun getCocktailById(id: Int) : Cocktail?

    @Query("select * from cocktail order by strDrink ASC")
    suspend fun getCocktails() : List<Cocktail>?

    //TODO : Delete, Update o Query
}