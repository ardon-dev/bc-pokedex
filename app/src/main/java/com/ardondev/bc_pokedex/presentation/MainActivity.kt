@file:OptIn(ExperimentalMaterial3Api::class)

package com.ardondev.bc_pokedex.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ardondev.bc_pokedex.R
import com.ardondev.bc_pokedex.presentation.screens.detail.DetailScreen
import com.ardondev.bc_pokedex.presentation.screens.home.HomeScreen
import com.ardondev.bc_pokedex.presentation.theme.BCPokedexTheme
import com.ardondev.bc_pokedex.presentation.theme.Typography
import com.ardondev.bc_pokedex.presentation.theme.blue
import com.ardondev.bc_pokedex.presentation.theme.navy
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
                MainNavigation(navHostController)
            }

        }
    }
}

@Composable
fun MainNavigation(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Routes.HomeScreen.route
    ) {

        //Home screen
        composable(Routes.HomeScreen.route) {
            HomeScreen(navController = navHostController)
        }

        //Detail screen
        composable(
            route = Routes.DetailScreen.route,
            arguments = listOf(
                navArgument("pokemon_id") {
                    type = NavType.IntType
                },
                navArgument("pokemon_name") {
                    type = NavType.StringType
                }
            )
        ) {
            DetailScreen(navController = navHostController)
        }

    }
}

@Composable
fun MainTopBar(
    navHostController: NavHostController
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val isInHome = navBackStackEntry?.destination?.route == Routes.HomeScreen.route
    if (isInHome) {
        HomeTopAppBar()
    }
}

@Composable
fun NavigationTopBar(
    title: String,
    onBack: () -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = title.capitalize(Locale.current),
                fontWeight = FontWeight.Bold,
                color = navy
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Regresar",
                    tint = navy
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = surface
        ),
        actions = actions
    )
}

@Composable
fun HomeTopAppBar() {
    TopAppBar(
        title = {
            Text(
                text = "Pokédex",
                style = Typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            Box(
                modifier =
                Modifier
                    .padding(start = 16.dp, end = 8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_pokeball),
                    contentDescription = null,
                    tint = blue,
                    modifier = Modifier.size(40.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = surface
        )
    )
}