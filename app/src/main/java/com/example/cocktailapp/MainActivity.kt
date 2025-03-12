package com.example.cocktailapp

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cocktailapp.database.CocktailDBService
import com.example.cocktailapp.items.BottomNavigationMenu
import com.example.cocktailapp.screens.Home
import com.example.cocktailapp.screens.Saved
import com.example.cocktailapp.screens.Screen2
import com.example.cocktailapp.ui.theme.CocktailAppTheme
import com.example.cocktailapp.viewModels.ApplicationViewModel

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CocktailAppTheme {

                CocktailDBService.init(this)

                val navController = rememberNavController()
                val appViewModel : ApplicationViewModel = viewModel()

                Scaffold (
                    bottomBar = {
                        BottomNavigationMenu(navController)
                    }
                ){
                    NavHost(navController = navController, startDestination = "Home"){
                        composable("Home") { Home(appViewModel) }
                        composable("Tutti i cocktail") { Screen2(navController, appViewModel) }
                        composable("Preferiti") { Saved(navController, appViewModel)}
                    }
                }
            }
        }
    }
}