package com.example.cocktailapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cocktailapp.database.entities.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredient: Ingredient) : Long

    @Query("select * from ingredient")
    suspend fun getIngredients() : List<Ingredient>

    @Query("select * from ingredient where id = :id")
    fun getIngredient(id: Int): Ingredient?

    @Query("select id from ingredient where name = :name")
    fun getIngredientIdByName(name: String): Int?

}