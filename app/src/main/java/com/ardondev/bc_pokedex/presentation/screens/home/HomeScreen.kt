@file:OptIn(ExperimentalMaterial3Api::class)

package com.ardondev.bc_pokedex.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.ardondev.bc_pokedex.R
import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import com.ardondev.bc_pokedex.presentation.components.LoadingView
import com.ardondev.bc_pokedex.presentation.theme.Typography
import com.ardondev.bc_pokedex.presentation.theme.navy
import com.ardondev.bc_pokedex.presentation.theme.yellow

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val pokemonPagingItems = viewModel.pokemonListState.collectAsLazyPagingItems()

    Column(Modifier.fillMaxSize()) {
        HomeHeader(viewModel)
        Spacer(Modifier.size(16.dp))
        HomePokemonList(pokemonPagingItems)
    }

}

@Composable
fun HomeHeader(
    viewModel: HomeViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "¡Hola, bienvenido!",
            style = Typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.size(16.dp))
        HomeSearchBar(viewModel)
    }
}

@Composable
fun HomeSearchBar(
    viewModel: HomeViewModel,
) {
    OutlinedTextField(
        value = viewModel.query,
        shape = CircleShape,
        visualTransformation = VisualTransformation.None,
        maxLines = 1,
        placeholder = {
            Text("Buscar")
        },
        trailingIcon = {
            Box(
                Modifier.background(
                    color = yellow, shape = CircleShape
                )
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp)
                )
            }
        },
        onValueChange = { newValue ->
            viewModel.setQueryValue(newValue)
        },
        modifier = Modifier
            .defaultMinSize(minHeight = 40.dp)
            .fillMaxWidth()
    )
}

@Composable
fun HomePokemonList(pokemonPagingItems: LazyPagingItems<Pokemon>) {
    pokemonPagingItems.apply {
        when {
            loadState.refresh is LoadState.Loading && pokemonPagingItems.itemCount == 0 -> {
                LoadingView()
            }

            loadState.refresh is LoadState.NotLoading && pokemonPagingItems.itemCount == 0 -> {
                val error = pokemonPagingItems.loadState.refresh as LoadState.Error
                Text(text = error.error.localizedMessage)
            }

            loadState.hasError -> {
                Text("Error")
            }

            else -> {
                PokemonList(pokemonPagingItems)

                if (pokemonPagingItems.loadState.append is LoadState.Loading) {
                    LoadingView()
                }
            }
        }
    }
}

@Composable
fun PokemonList(pokemonPagingItems: LazyPagingItems<Pokemon>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            count = pokemonPagingItems.itemCount,
            key = { index ->
                pokemonPagingItems[index]!!.id!!
            }
        ) { index ->
            pokemonPagingItems[index]?.let { pokemon ->
                PokemonItem(pokemon)
            }
        }
    }
}

@Composable
fun PokemonItem(pokemon: Pokemon) {
    Card(
        onClick = {

        }, colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "#${pokemon.id}",
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFFAAAAAA),
                modifier = Modifier.fillMaxWidth()
            )
            AsyncImage(
                model = pokemon.getSprite(),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.CenterHorizontally),
                error = painterResource(R.drawable.ic_launcher_background)
            )
            Text(
                text = pokemon.name?.capitalize(Locale.current).orEmpty(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = navy,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            )
        }

    }
}