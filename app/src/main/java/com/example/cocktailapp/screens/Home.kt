package com.example.cocktailapp.screens

import com.example.cocktailapp.database.entities.Cocktail
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cocktailapp.api.RetrofitInstance
import com.example.cocktailapp.database.CocktailDBService
import com.example.cocktailapp.database.DatabaseRepository
import com.example.cocktailapp.database.entities.SavedCocktail
import com.example.cocktailapp.items.CocktailCard
import com.example.cocktailapp.items.ListString
import com.example.cocktailapp.items.OptionList
import com.example.cocktailapp.viewModels.ApplicationViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Home(viewModel: ApplicationViewModel = viewModel()){

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val serviceDB = CocktailDBService.getRepository()

    Column{
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .padding(bottom = 20.dp),
                text = "Cocktail APP",
                textAlign = TextAlign.Center,
                fontSize = 50.sp,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text("Cerca cocktail per : ")
                viewModel.selectedOption = OptionList(viewModel.options, viewModel)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp)
                .padding(bottom = 150.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
        ) {

            CocktailCard(
                imageUrl = viewModel.imageUrl,
                cocktailName = viewModel.cocktailName,
                imageSize = 200
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text("Ingredienti: ")
                ListString( viewModel.ingredients )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Text("Istruzioni: ")
                Text(viewModel.instructions)
            }

            if(!viewModel.selectedOption.contentEquals("random")) {
                TextField(
                    value = viewModel.text,
                    onValueChange = { viewModel.text = it },
                    label = { Text("Campo di ricerca: ") },
                    placeholder = { Text("Type something...") },
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
            ){
                Button(
                    onClick = {
                        val apiService = RetrofitInstance.api

                        viewModel.text = viewModel.text.trim()
                        if(viewModel.text == "" && viewModel.selectedOption != "random")
                            return@Button

                        when (viewModel.selectedOption){
                            "nome", -> {
                                coroutineScope.launch {
                                    viewModel.currentCocktail = apiService.getCocktailByName(viewModel.text).drinks?.firstOrNull()
                                    changeValues(viewModel, viewModel.currentCocktail, serviceDB)
                                }
                            }
                            "id", -> {
                                coroutineScope.launch {
                                    if (viewModel.text.toIntOrNull() == null)
                                        viewModel.currentCocktail = null
                                    else
                                        viewModel.currentCocktail = apiService.getCocktailById(viewModel.text).drinks?.firstOrNull()
                                    changeValues(viewModel, viewModel.currentCocktail, serviceDB)
                                }
                            }
                            "random", -> {
                                coroutineScope.launch {
                                    viewModel.currentCocktail = apiService.getRandomCocktail().drinks?.firstOrNull()
                                    changeValues(viewModel, viewModel.currentCocktail, serviceDB)
                                }
                            }
                        }
                    }
                ) {
                    Text( text = "Cerca" )
                }

                val context = LocalContext.current
                if(viewModel.cocktailName != "not found" && viewModel.cocktailName != ""){
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val cocktailExists = serviceDB.getCocktailById(viewModel.currentCocktail!!.idDrink) != null

                                if (!cocktailExists) {
                                    serviceDB.insertCocktail(Cocktail(viewModel.currentCocktail!!.idDrink, viewModel.currentCocktail!!.strDrink))
                                }

                                if (!serviceDB.getSavedCocktails().contains(viewModel.currentCocktail!!.idDrink)){
                                    serviceDB.insertSavedCocktail(SavedCocktail(viewModel.currentCocktail!!.idDrink))
                                    Toast.makeText(context, "Cocktail aggiunto ai preferiti", Toast.LENGTH_SHORT).show()
                                    viewModel.isSaved = true
                                }
                                else{
                                    serviceDB.deleteSavedCocktail(SavedCocktail(viewModel.currentCocktail!!.idDrink))
                                    Toast.makeText(context, "Cocktail rimosso dai preferiti", Toast.LENGTH_SHORT).show()
                                    viewModel.isSaved = false
                                }
                            }

                        }
                    ) {
                        LaunchedEffect(Unit) {
                            if(serviceDB.getSavedCocktails().contains(viewModel.currentCocktail!!.idDrink))
                                viewModel.isSaved = true
                            else
                                viewModel.isSaved = false
                        }
                        if(!viewModel.isSaved)
                            Text( text = "Salva" )
                        else
                            Text( text = "Rimuovi")
                    }
                }
            }
        }
    }
}

suspend fun changeValues(viewModel: ApplicationViewModel, cocktail: Cocktail?, serviceDB: DatabaseRepository) {
    viewModel.imageUrl = cocktail?.strDrinkThumb ?: "not found"
    viewModel.cocktailName = cocktail?.strDrink ?: "not found"
    viewModel.ingredients = cocktail?.getIngredientsList() ?: listOf("", "", "", "")
    viewModel.instructions = cocktail?.strInstructionsIT ?: "not found"
    viewModel.text = ""

    // Aggiornamento dello stato di isSaved
    val cocktailId = cocktail?.idDrink
    if (cocktailId != null) {
        val isCocktailSaved = serviceDB.getSavedCocktails().contains(cocktailId)
        viewModel.isSaved = isCocktailSaved
    }
}