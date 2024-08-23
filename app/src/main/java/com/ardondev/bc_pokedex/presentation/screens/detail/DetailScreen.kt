@file:OptIn(ExperimentalMaterial3Api::class)

package com.ardondev.bc_pokedex.presentation.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ardondev.bc_pokedex.R
import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import com.ardondev.bc_pokedex.domain.model.pokemon.Stat
import com.ardondev.bc_pokedex.presentation.components.ErrorView
import com.ardondev.bc_pokedex.presentation.components.LoadingView
import com.ardondev.bc_pokedex.presentation.theme.blue
import com.ardondev.bc_pokedex.presentation.theme.navy
import com.ardondev.bc_pokedex.presentation.util.UiState
import com.ardondev.bc_pokedex.presentation.util.getSprite

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        is UiState.Loading -> LoadingView()
        is UiState.Success -> {
            val pokemon = (uiState as UiState.Success).data
            DetailContent(pokemon)
        }
        is UiState.Error ->  {
            val error = (uiState as UiState.Error).exception.message
            ErrorView(error.orEmpty()) {
                viewModel.getPokemonDetail()
            }
        }
    }
}

@Preview
@Composable
fun DetailContentPreview() {
    val pokemon = Pokemon("a", "s", 5)
    DetailContent(pokemon = pokemon)
}

@Composable
fun DetailContent(
    pokemon: Pokemon
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
        DetailHead(pokemon)
        Spacer(Modifier.size(16.dp))
        DetailPokemonProperties(
            weight = pokemon.weight ?: 0,
            height = pokemon.height ?: 0
        )
        Spacer(Modifier.size(16.dp))
        DetailDescription()
        Spacer(Modifier.size(16.dp))
        DetailStatistics(pokemon.stats.orEmpty())
    }
}

@Composable
fun DetailHead(
    pokemon: Pokemon
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        val (sprite, type, background) = createRefs()

        Box(
            modifier = Modifier
                .background(
                    color = Color.Magenta,
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
            model = getSprite(pokemon.id!!),
            contentDescription = null,
            error = painterResource(R.drawable.ic_pokeball),
            placeholder = painterResource(R.drawable.ic_pokeball),
            modifier = Modifier
                .size(200.dp)
                .constrainAs(sprite) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        )

        FilterChip(
            selected = false,
            onClick = { },
            label = { Text(text = "Type") },
            modifier = Modifier
                .constrainAs(type) {
                    top.linkTo(sprite.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

    }
}

@Composable
fun DetailPokemonProperties(
    weight: Int,
    height: Int
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
fun DetailDescription() {
    Text(
        text = "La figura de Charizard es la de un dragón erguido sobre sus dos patas traseras, que sostienen su peso. Posee unas poderosas alas y un abrasador aliento de fuego.",
        style = MaterialTheme.typography.bodyMedium,
        color = Color(0xFF7C7C7C),
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun DetailStatistics(
    stats: List<Stat>
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
            StatisticBar(label = name, value = it.base!!)
        }
    }
}

@Composable
fun StatisticBar(
    label: String,
    value: Int,
) {
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
        //Bar
        LinearProgressIndicator(
            progress = (value * 0.01).toFloat(),
            strokeCap = StrokeCap.Round,
            modifier = Modifier
                .height(14.dp)
                .weight(1.5f)
        )
        Spacer(Modifier.size(8.dp))
        //Value
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF404040),
        )
    }
}

