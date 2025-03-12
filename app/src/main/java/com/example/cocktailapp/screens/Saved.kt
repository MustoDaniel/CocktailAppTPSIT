package com.example.cocktailapp.screens

import com.example.cocktailapp.database.entities.Cocktail
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cocktailapp.R
import com.example.cocktailapp.api.RetrofitInstance
import com.example.cocktailapp.database.CocktailDBService
import com.example.cocktailapp.items.CocktailList
import com.example.cocktailapp.items.Gif
import com.example.cocktailapp.viewModels.ApplicationViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Saved(navController: NavController, appViewModel: ApplicationViewModel){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 130.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            modifier = Modifier
                .padding(top = 40.dp)
                .padding(bottom = 20.dp),
            text = "Preferiti",
            textAlign = TextAlign.Center,
            fontSize = 50.sp,
        )
        val serviceDB = CocktailDBService.getRepository()
        val apiService = RetrofitInstance.api
        val cocktails = remember { mutableStateListOf<Cocktail>() }
        val cocktailsID = remember { mutableStateListOf<Int>() }

        LaunchedEffect(Unit) {
            cocktailsID.addAll(serviceDB.getSavedCocktails())
            if(!cocktailsID.isEmpty()){
                cocktailsID.forEach{ id ->
                    cocktails.add(apiService.getCocktailById(id.toString()).drinks!!.first())
                }
            }
        }

        if(cocktailsID.isEmpty()){
            Text("Nessun cocktail salvato")
        }
        else if(cocktails.isEmpty()) {
            Gif(R.drawable.loading_image)
        } else{
            CocktailList(
                cocktailList = cocktails,
                modifier = Modifier.fillMaxHeight(),
                navController = navController,
                viewModel = appViewModel
            )
        }
    }
}