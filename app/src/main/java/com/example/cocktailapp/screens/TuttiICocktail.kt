package com.example.cocktailapp.screens

import com.example.cocktailapp.database.entities.Cocktail
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cocktailapp.R
import com.example.cocktailapp.data.Datasource
import com.example.cocktailapp.database.CocktailDBService
import com.example.cocktailapp.database.DatabaseRepository
import com.example.cocktailapp.database.entities.Ingredient
import com.example.cocktailapp.items.CocktailList
import com.example.cocktailapp.items.Gif
import com.example.cocktailapp.items.OptionList
import com.example.cocktailapp.viewModels.ApplicationViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Screen2(navController: NavController, viewModel: ApplicationViewModel){

    val serviceDB = CocktailDBService.getRepository()

    var cocktails by remember { mutableStateOf<List<Cocktail>>(emptyList()) }

    var nonAlcoholic by remember { mutableStateOf(false) }
    var showFilters by remember { mutableStateOf(false) }

    var ingredients by remember { mutableStateOf<List<Ingredient>>(emptyList()) }

    LaunchedEffect(Unit){
        cocktails = serviceDB.getCocktails()!!.toMutableList()
        ingredients = serviceDB.getIngredients()
    }

    LaunchedEffect( showFilters ){
        if(!showFilters){
            cocktails = serviceDB.getCocktails()!!

            val checkedIngredientList = viewModel.checkedIngredients.map { it.name }
            val filteredCocktails = emptyList<Cocktail>().toMutableList()

            cocktails.forEach{ cocktail ->
                if(cocktail.getIngredientsList().containsAll(checkedIngredientList))
                    filteredCocktails.add(cocktail)
            }

            cocktails = filteredCocktails
        }
    }


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
                text = "Tutti i Cocktail",
                textAlign = TextAlign.Center,
                fontSize = 50.sp,
            )
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = "Filtri:",
                textAlign = TextAlign.Left,
                fontSize = 25.sp,
            )

            //CHECKBOX

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally)
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Checkbox(
                        checked = nonAlcoholic,
                        onCheckedChange = {
                            nonAlcoholic = it
                        }
                    )
                    Text(
                        "Senza alcol"
                    )

                    LaunchedEffect(nonAlcoholic) {
                        if(nonAlcoholic && cocktails.isNotEmpty()){
                            val newCocktails = emptyList<Cocktail>().toMutableList()
                            cocktails.forEach{ cocktail ->
                                if(cocktail.strAlcoholic != "Alcoholic")
                                    newCocktails.add(cocktail)
                            }
                            cocktails = newCocktails
                        }
                        else {
                            cocktails = serviceDB.getCocktails()!!

                            val checkedIngredientList = viewModel.checkedIngredients.map { it.name }
                            val filteredCocktails = emptyList<Cocktail>().toMutableList()

                            cocktails.forEach{ cocktail ->
                                if(cocktail.getIngredientsList().containsAll(checkedIngredientList))
                                    filteredCocktails.add(cocktail)
                            }

                            cocktails = filteredCocktails
                        }
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Button(
                        onClick = {
                            showFilters = !showFilters
                        }
                    ) {
                        Text("Ingredienti")
                    }
                }
            }

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 130.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (cocktails.isEmpty() && viewModel.checkedIngredients.isEmpty()) {
                Gif(R.drawable.loading_image)
            }
            else if (cocktails.isEmpty()){
                Text(
                    "Nessun cocktail trovato con gli ingredienti selezionati"
                )
            }
            else{
                CocktailList(
                    cocktailList = cocktails,
                    modifier = Modifier.fillMaxHeight(),
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }

    if(showFilters){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.7f))
                .pointerInput(Unit) {}, // Blocca interazione con la Box
        ){
            Column(
                modifier = Modifier.align(Alignment.Center)
            ){
                Text(
                    "Seleziona gli ingredienti per filtrare i cocktail che li contengono",
                    modifier = Modifier
                        .width(300.dp)
                        .padding(bottom = 10.dp),
                    fontSize = 20.sp,
                    color = Color.White,
                    softWrap = true
                )
                Card(
                    modifier = Modifier
                        .width(300.dp)
                        .height(300.dp)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
                    ) {
                        items(ingredients){ ingredient ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Absolute.Left
                            ) {
                                Checkbox(
                                    checked = viewModel.checkedIngredients.contains(ingredient),
                                    onCheckedChange = { isChecked ->
                                        if(isChecked)
                                            viewModel.checkedIngredients += ingredient
                                        else
                                            viewModel.checkedIngredients -= ingredient
                                    }
                                )
                                Text(
                                    ingredient.name
                                )
                            }
                        }
                    }
                }
                Button(
                    onClick = {
                        showFilters = !showFilters
                    }
                ) {
                    Text("Applica")
                }
            }
        }
    }
}
