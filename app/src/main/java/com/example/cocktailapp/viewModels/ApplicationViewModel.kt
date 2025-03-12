package com.example.cocktailapp.viewModels

import com.example.cocktailapp.database.entities.Cocktail
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cocktailapp.database.entities.Ingredient

class ApplicationViewModel : ViewModel() {

    //Schermata HOME
    val options by mutableStateOf(listOf("nome", "id", "random"))
    var selectedOption = options[0]
    var imageUrl by mutableStateOf("")
    var cocktailName by mutableStateOf("")
    var ingredients by mutableStateOf(listOf("","","",""))
    var instructions by mutableStateOf("")
    var text by mutableStateOf("")
    var currentCocktail : Cocktail? = Cocktail()
    var isSaved by mutableStateOf(false)


    //schermata Tutti i cocktail
    var checkedIngredients by mutableStateOf<List<Ingredient>>(emptyList())  // Lista di ingredienti selezionati

}