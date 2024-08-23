package com.ardondev.bc_pokedex.presentation.components

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.ardondev.bc_pokedex.R

@Composable
fun LoadingView() {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            shape = CircleShape,
            modifier = Modifier.size(100.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(context)
                        .data(R.drawable.loading)
                        .apply(block = {
                            size(Size.ORIGINAL)
                        }).build(),
                    imageLoader = imageLoader
                ),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Composable
fun ErrorView(
    message: String,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_pokeball),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
                modifier = Modifier.size(40.dp)
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.size(16.dp))
            Button(
                onClick = onClick
            ) {
                Text("Retry")
            }
        }
    }
}
