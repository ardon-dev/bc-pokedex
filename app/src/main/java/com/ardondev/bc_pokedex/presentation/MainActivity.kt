@file:OptIn(ExperimentalMaterial3Api::class)

package com.ardondev.bc_pokedex.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ardondev.bc_pokedex.R
import com.ardondev.bc_pokedex.presentation.screens.home.HomeScreen
import com.ardondev.bc_pokedex.presentation.theme.BCPokedexTheme
import com.ardondev.bc_pokedex.presentation.theme.Typography
import com.ardondev.bc_pokedex.presentation.theme.surface
import com.ardondev.bc_pokedex.presentation.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navHostController = rememberNavController()

            BCPokedexTheme {
                Scaffold(
                    topBar = { MainTopBar() },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    MainNavigation(navHostController, innerPadding)
                }
            }

        }
    }
}

@Composable
fun MainNavigation(
    navHostController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.HomeScreen.route,
        modifier = Modifier.padding(innerPadding)
    ) {

        //Home screen
        composable(Routes.HomeScreen.route) {
            HomeScreen()
        }

    }
}

@Composable
fun MainTopBar() {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_pokeball),
                    contentDescription = null
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = "Pokédex",
                    style = Typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = surface
        )
    )
}