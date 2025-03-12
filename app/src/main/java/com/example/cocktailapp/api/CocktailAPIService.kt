package com.example.cocktailapp.api

import com.example.cocktailapp.data.CocktailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailAPIService {
    @GET("search.php")
    suspend fun getCocktailsByFirstLetter(@Query("f") firstLetter: Char): CocktailResponse

    @GET("search.php")
    suspend fun getCocktailByName(@Query("s") name: String) : CocktailResponse

    @GET("lookup.php")
    suspend fun getCocktailById(@Query("i") id: String) : CocktailResponse

    @GET("random.php")
    suspend fun getRandomCocktail() : CocktailResponse
}