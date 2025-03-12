package com.example.cocktailapp.database

import android.content.Context
import com.example.cocktailapp.data.Datasource

object CocktailDBService {

    private lateinit var database: CocktailDB
    private var repository: DatabaseRepository? = null

    fun init(context: Context){
        database = CocktailDB.getDatabase(context.applicationContext)
        repository = DatabaseRepository(
            database.cocktailDao(),
            database.ingredientDao(),
            database.savedCocktailDao(),
            database.cocktail_IngredientDao()
        )
    }

    fun getRepository() : DatabaseRepository {
        checkNotNull(repository){
            "Il database non è ancora stato inizializzato, assicurati di aver chiamato la funzione init(context)"
        }
        return repository!!
    }

}