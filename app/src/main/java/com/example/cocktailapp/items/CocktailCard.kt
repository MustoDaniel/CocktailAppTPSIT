package com.example.cocktailapp.items

import com.example.cocktailapp.database.entities.Cocktail
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cocktailapp.R
import com.example.cocktailapp.viewModels.ApplicationViewModel


//Card utilizzata da CocktailList
@Composable
fun CocktailCard(
    cocktail: Cocktail,
    imageSize: Int,
    navController: NavController,
    viewModel: ApplicationViewModel,
    modifier: Modifier = Modifier,
    )
{
    Card(
        modifier = modifier
            .width(imageSize.dp),
        onClick = {
            viewModel.cocktailName = cocktail.strDrink
            viewModel.ingredients = cocktail.getIngredientsList()
            viewModel.instructions = cocktail.strInstructionsIT ?: "not found"
            viewModel.imageUrl = cocktail.strDrinkThumb ?: "not found"
            viewModel.currentCocktail = cocktail

            navController.navigate("Home")
        }
    ){
        Column {
            AsyncImage(
                model = cocktail.strDrinkThumb,
                contentDescription = "Immagine cocktail",
                placeholder = painterResource(id = R.drawable.image_not_found),
                error = painterResource(id = R.drawable.image_not_found),
                modifier = Modifier
                    .width(imageSize.dp)
                    .height(imageSize.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = cocktail.strDrink + " (${cocktail.idDrink})",
                textAlign = TextAlign.Center,
                softWrap = true,
            )
        }
    }
}


//Card utilizzata dalla pagina Home
@Composable
fun CocktailCard(imageUrl: String, cocktailName: String, imageSize: Int, modifier: Modifier = Modifier){

    Card(
        modifier = modifier
            .width(imageSize.dp),
    ){
        Column {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Immagine cocktail",
                placeholder = painterResource(id = R.drawable.image_not_found),
                error = painterResource(id = R.drawable.image_not_found),
                modifier = Modifier
                    .width(imageSize.dp)
                    .height(imageSize.dp),
                contentScale = ContentScale.FillBounds
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = cocktailName,
                textAlign = TextAlign.Center,
                softWrap = true,
            )
        }
    }
}