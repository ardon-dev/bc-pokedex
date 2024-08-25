@file:OptIn(ExperimentalMaterial3Api::class)

package com.ardondev.bc_pokedex.presentation.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.ardondev.bc_pokedex.R
import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import com.ardondev.bc_pokedex.presentation.HomeTopAppBar
import com.ardondev.bc_pokedex.presentation.components.ErrorView
import com.ardondev.bc_pokedex.presentation.components.LoadingView
import com.ardondev.bc_pokedex.presentation.components.SearchLoadingView
import com.ardondev.bc_pokedex.presentation.theme.navy
import com.ardondev.bc_pokedex.presentation.theme.surface
import com.ardondev.bc_pokedex.presentation.theme.yellow
import com.ardondev.bc_pokedex.presentation.util.Routes
import com.ardondev.bc_pokedex.presentation.util.formatPokemonId
import com.ardondev.bc_pokedex.presentation.util.getWelcomeText
import com.gigamole.composefadingedges.FadingEdgesGravity
import com.gigamole.composefadingedges.fill.FadingEdgesFillType
import com.gigamole.composefadingedges.verticalFadingEdges

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {

    val pokemonPagingItems = viewModel.pokemonListState.collectAsLazyPagingItems()

    Scaffold(
        topBar = { HomeTopAppBar() }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HomeHeader(viewModel)
            Spacer(Modifier.size(8.dp))
            HomePokemonList(
                viewModel = viewModel,
                pokemonPagingItems = pokemonPagingItems,
                onRetry = {
                    viewModel.getPokemonList()
                },
                onSelect = { pokemon ->
                    navController.navigate(
                        Routes.DetailScreen.createRoute(
                            pokemonId = pokemon.id ?: -1,
                            pokemonName = pokemon.name ?: ""
                        )
                    )
                }
            )
        }
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
        Spacer(Modifier.size(16.dp))
        Text(
            text = getWelcomeText(),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.size(16.dp))
        HomeSearchBar(viewModel)
    }
}

@Composable
fun HomeSearchBar(
    viewModel: HomeViewModel,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = viewModel.query,
        shape = CircleShape,
        visualTransformation = VisualTransformation.None,
        maxLines = 1,
        placeholder = {
            Text(stringResource(R.string.txt_search))
        },
        trailingIcon = {
            IconButton(
                onClick = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                    if (viewModel.searchActive) viewModel.setQueryValue("")
                    viewModel.getPokemonList()
                },
                modifier = Modifier
                    .padding(end = 2.dp)
                    .border(
                        border = BorderStroke(6.dp, surface),
                        shape = CircleShape
                    )
                    .background(
                        color = yellow,
                        shape = CircleShape
                    )
            ) {
                val res = if (viewModel.searchActive) R.drawable.ic_close else R.drawable.ic_search
                Icon(
                    painter = painterResource(res),
                    contentDescription = null
                )
            }
        },
        onValueChange = { newValue ->
            viewModel.setQueryValue(newValue.trim())
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                focusManager.clearFocus()
                viewModel.getPokemonList()
            }
        ),
        modifier = Modifier
            .defaultMinSize(minHeight = 40.dp)
            .fillMaxWidth()
    )
}

@Composable
fun HomePokemonList(
    viewModel: HomeViewModel,
    pokemonPagingItems: LazyPagingItems<Pokemon>,
    onRetry: () -> Unit,
    onSelect: (Pokemon) -> Unit,
) {
    pokemonPagingItems.apply {

        //Log.d("Home", "States: loadState.refresh = ${loadState.refresh}, loadState.append = ${loadState.append}, itemCount = ${pokemonPagingItems.itemCount}")

        when {
            loadState.refresh is LoadState.Loading && pokemonPagingItems.itemCount == 0 -> {
                if (viewModel.query.isEmpty()) {
                    LoadingView()
                } else {
                    SearchLoadingView(pokemonPagingItems.itemCount)
                }
            }

            loadState.refresh is LoadState.NotLoading && pokemonPagingItems.itemCount == 0 -> {
                if (!viewModel.searchActive) {
                    ErrorView(
                        message = stringResource(R.string.txt_no_results),
                        onClick = onRetry
                    )
                } else {
                    if (loadState.append.endOfPaginationReached) {
                        ErrorView(
                            message = stringResource(R.string.txt_no_results),
                            onClick = onRetry
                        )
                    } else {
                        SearchLoadingView(pokemonPagingItems.itemCount)
                    }
                }
            }

            loadState.append is LoadState.Loading && pokemonPagingItems.itemCount == 0 -> {
                if (!viewModel.searchActive) {
                    LoadingView()
                } else {
                    SearchLoadingView(pokemonPagingItems.itemCount)
                }
            }

            loadState.hasError -> {
                val error = pokemonPagingItems.loadState.refresh as LoadState.Error
                ErrorView(
                    message = error.error.message.orEmpty(),
                    onClick = onRetry
                )
            }

            else -> {
                PokemonList(pokemonPagingItems, onSelect)

                if (pokemonPagingItems.loadState.append is LoadState.Loading) {
                    if (!viewModel.searchActive) {
                        LoadingView()
                    } else {
                        SearchLoadingView(pokemonPagingItems.itemCount)
                    }
                }

            }
        }
    }
}

@Composable
fun PokemonList(
    pokemonPagingItems: LazyPagingItems<Pokemon>,
    onSelect: (Pokemon) -> Unit,
) {
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Adaptive(minSize = 128.dp),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .verticalFadingEdges(
                gravity = FadingEdgesGravity.All,
                length = 36.dp,
                fillType = FadingEdgesFillType.FadeColor(
                    color = surface
                )
            ),
    ) {
        items(
            count = pokemonPagingItems.itemCount,
            key = { index ->
                pokemonPagingItems[index]?.id ?: -1
            }
        ) { index ->

            pokemonPagingItems[index]?.let { pokemon ->
                PokemonItem(
                    pokemon = pokemon,
                    onSelect = onSelect
                )
            }
        }
    }
}

@Composable
fun PokemonItem(
    pokemon: Pokemon,
    onSelect: (Pokemon) -> Unit,
) {
    Card(
        onClick = {
            onSelect(pokemon)
        },
        colors = CardDefaults
            .cardColors(
                containerColor = Color.White
            ),
        modifier = Modifier
            .shadow(
                elevation = 0.1.dp,
                shape = MaterialTheme.shapes.medium,
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary
            )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = formatPokemonId(pokemon.id ?: 0),
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
                error = painterResource(R.drawable.ic_pokeball),
                placeholder = painterResource(R.drawable.ic_pokeball),
            )
            Text(
                text = pokemon.name?.capitalize(Locale.current).orEmpty(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = navy,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            )
        }

    }
}