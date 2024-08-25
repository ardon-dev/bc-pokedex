@file:OptIn(ExperimentalMaterial3Api::class)

package com.ardondev.bc_pokedex.presentation.screens.detail

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ardondev.bc_pokedex.R
import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import com.ardondev.bc_pokedex.domain.model.pokemon.Stat
import com.ardondev.bc_pokedex.domain.model.pokemon.Type
import com.ardondev.bc_pokedex.presentation.NavigationTopBar
import com.ardondev.bc_pokedex.presentation.components.ErrorView
import com.ardondev.bc_pokedex.presentation.components.LoadingView
import com.ardondev.bc_pokedex.presentation.theme.blue
import com.ardondev.bc_pokedex.presentation.theme.navy
import com.ardondev.bc_pokedex.presentation.util.UiState
import com.ardondev.bc_pokedex.presentation.util.formatPokemonId
import com.ardondev.bc_pokedex.presentation.util.getColorByType
import com.ardondev.bc_pokedex.presentation.util.getNameByType
import com.ardondev.bc_pokedex.presentation.util.getSprite
import kotlinx.coroutines.delay

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    navController: NavController,
) {

    val uiState by viewModel.uiState.collectAsState()
    val textEntry by viewModel.textEntry.collectAsState()

    Scaffold(
        topBar = {
            NavigationTopBar(
                title = viewModel.pokemonName,
                onBack = {
                    navController.navigateUp()
                },
                actions = {
                    Text(
                        text = formatPokemonId(viewModel.pokemonId),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFFAAAAAA),
                        modifier = Modifier
                            .padding(end = 24.dp)
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState) {
                is UiState.Loading -> LoadingView()
                is UiState.Success -> {
                    val pokemon = (uiState as UiState.Success).data
                    DetailContent(
                        pokemon = pokemon,
                        textEntry = textEntry
                    )
                }

                is UiState.Error -> {
                    val error = (uiState as UiState.Error).exception.message
                    ErrorView(error.orEmpty()) {
                        viewModel.getPokemonDetail()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailContentPreview() {
    val pokemon = Pokemon("a", "s", 5)
    DetailContent(pokemon = pokemon, "")
}

@Composable
fun DetailContent(
    pokemon: Pokemon,
    textEntry: String,
) {

    val scrollState = rememberScrollState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(
                vertical = 16.dp,
                horizontal = 24.dp
            )
    ) {
        val primaryTypeId = pokemon.types?.getOrNull(0)?.id ?: 0

        DetailHead(
            pokemonId = pokemon.id ?: -1,
            primaryTypeId = primaryTypeId,
            types = pokemon.types.orEmpty()
        )
        Spacer(Modifier.size(16.dp))
        DetailPokemonProperties(
            weight = pokemon.weight ?: 0,
            height = pokemon.height ?: 0
        )
        Spacer(Modifier.size(16.dp))
        DetailDescription(textEntry)
        Spacer(Modifier.size(16.dp))
        DetailStatistics(
            stats = pokemon.stats.orEmpty(),
            typeId = primaryTypeId
        )
    }
}

@Composable
fun DetailHead(
    pokemonId: Int,
    primaryTypeId: Int,
    types: List<Type>,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        val (sprite, type, background) = createRefs()

        Box(
            modifier = Modifier
                .background(
                    color = getColorByType(primaryTypeId, true),
                    shape = MaterialTheme.shapes.large
                )
                .fillMaxWidth()
                .height(150.dp)
                .constrainAs(background) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )

        AsyncImage(
            model = getSprite(pokemonId),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .constrainAs(sprite) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )

        //Types
        Log.d("DetailScreen", types.toString())
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .constrainAs(type) {
                    top.linkTo(sprite.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            types.forEach { pokemonType ->
                if (types.size > 1 && types.indexOf(pokemonType) == 1) {
                    Spacer(Modifier.size(8.dp))
                }
                PokemonType(
                    pokemonType = pokemonType,
                    modifier = Modifier
                )
            }
        }

    }

}

@Composable
fun PokemonType(
    modifier: Modifier,
    pokemonType: Type,
) {
    FilterChip(
        shape = MaterialTheme.shapes.extraLarge,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = getColorByType(pokemonType.id!!, true),
            labelColor = getColorByType(pokemonType.id),
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = getColorByType(pokemonType.id)
        ),
        selected = false,
        onClick = { },
        label = {
            Text(
                text = getNameByType(pokemonType.id)
            )
        },
        modifier = modifier
    )
}

@Composable
fun DetailPokemonProperties(
    weight: Int,
    height: Int,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(Modifier.weight(1f)) {
                DetailProperty(
                    resId = R.drawable.ic_weight,
                    label = stringResource(R.string.txt_weight),
                    value = (weight / 10).toString() + " Kg"
                )
            }
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
            )
            Box(Modifier.weight(1f)) {
                DetailProperty(
                    resId = R.drawable.ic_rule,
                    label = stringResource(R.string.txt_height),
                    value = (height.toFloat() / 10).toString() + " m"
                )
            }
        }
    }
}

@Composable
fun DetailProperty(
    resId: Int,
    label: String,
    value: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(resId),
            contentDescription = null
        )
        Spacer(Modifier.size(8.dp))
        Column {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = navy
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Light,
                color = blue
            )
        }
    }
}

@Composable
fun DetailDescription(
    text: String,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = Color(0xFF7C7C7C),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun DetailStatistics(
    stats: List<Stat>,
    typeId: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Text(
            text = "Estadísticas",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = navy
        )
        Spacer(Modifier.size(16.dp))
        stats.forEach {
            val name = when (it.id) {
                1 -> "HP"
                2 -> "Ataque"
                3 -> "Defensa"
                4 -> "Ataque especial"
                5 -> "Defensa especial"
                else -> "Velocidad"
            }
            StatisticBar(
                label = name,
                value = it.base!!,
                type = typeId
            )
        }
    }
}

@Composable
fun StatisticBar(
    label: String,
    value: Int,
    type: Int,
) {

    var progress by rememberSaveable {
        mutableStateOf(0f)
    }

    val animateProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
    ) {
        //HP
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = Color(0xFF404040),
            modifier = Modifier
                .weight(1f)
        )
        Spacer(Modifier.size(8.dp))

        LaunchedEffect(Unit) {
            progress = (value / 255f) * 100
        }

        LinearProgressIndicator(
            progress = animateProgress,
            color = getColorByType(type),
            trackColor = getColorByType(type, true),
            strokeCap = StrokeCap.Round,
            modifier = Modifier
                .height(14.dp)
                .weight(1.5f)
        )
        //Value
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF404040),
            modifier = Modifier
                .weight(0.3f)
        )
    }
}

