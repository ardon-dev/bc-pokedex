package com.ardondev.bc_pokedex.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.ardondev.bc_pokedex.R
import com.ardondev.bc_pokedex.presentation.theme.BCPokedexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val pokemonPagingItems = viewModel.pokemonListState.collectAsLazyPagingItems()

            BCPokedexTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn(Modifier.fillMaxSize()) {
                        items(pokemonPagingItems.itemCount) { index ->
                            val pokemon = pokemonPagingItems[index]
                            Column {
                                AsyncImage(
                                    model = pokemon?.getSprite(),
                                    contentDescription = null,
                                    modifier = Modifier.size(100.dp),
                                    error = painterResource(R.drawable.ic_launcher_background)
                                )
                                Text("${pokemon?.id}: ${pokemon?.name}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BCPokedexTheme {
        Greeting("Android")
    }
}