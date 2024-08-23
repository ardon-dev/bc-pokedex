@file:OptIn(ExperimentalMaterial3Api::class)

package com.ardondev.bc_pokedex.presentation.screens.detail

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import com.ardondev.bc_pokedex.R
import com.ardondev.bc_pokedex.domain.model.pokemon.Pokemon
import com.ardondev.bc_pokedex.presentation.theme.blue
import com.ardondev.bc_pokedex.presentation.theme.navy

@Preview
@Composable
fun DetailScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(
                vertical = 16.dp,
                horizontal = 24.dp
            )
    ) {
        DetailHead()
        Spacer(Modifier.size(16.dp))
        DetailPokemonProperties()
        Spacer(Modifier.size(16.dp))
        DetailDescription()
        Spacer(Modifier.size(16.dp))
        DetailStatistics()
    }
}

@Composable
fun DetailHead(
    pokemon: Pokemon? = null,
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
            model = pokemon?.getSprite(),
            contentDescription = null,
            error = painterResource(R.drawable.ic_pokeball),
            placeholder = painterResource(R.drawable.ic_pokeball),
            modifier = Modifier
                .size(150.dp)
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
fun DetailPokemonProperties() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(Modifier.weight(1f)) {
                DetailProperty(
                    resId = R.drawable.ic_weight,
                    label = stringResource(R.string.txt_weight),
                    value = "110kg"
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
                    value = "1,71 m"
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
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = navy
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
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
fun DetailStatistics() {
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
        StatisticBar(label = "HP", value = "100")
    }
}

@Composable
fun StatisticBar(
    label: String,
    value: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(bottom = 8.dp)
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
            progress = 0.5f,
            strokeCap = StrokeCap.Round,
            modifier = Modifier
                .height(12.dp)
                .weight(2f)
        )
        Spacer(Modifier.size(8.dp))
        //Value
        Text(
            text = value,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF404040),
        )
    }
}

