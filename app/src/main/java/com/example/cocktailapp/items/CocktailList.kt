package com.example.cocktailapp.items

import com.example.cocktailapp.database.entities.Cocktail
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cocktailapp.viewModels.ApplicationViewModel

@Composable
fun CocktailList(cocktailList: List<Cocktail>, modifier: Modifier = Modifier, navController: NavController, viewModel: ApplicationViewModel){
    val imageSize = 150
    LazyVerticalGrid(
        columns = GridCells.FixedSize(imageSize.dp),
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
    ) {
        items(cocktailList){ cocktail ->
            CocktailCard(cocktail, imageSize, navController, viewModel)
        }
    }
}