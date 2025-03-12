package com.example.cocktailapp.items

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.cocktailapp.R

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun Gif(drawableId: Int){
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(drawableId)
            .decoderFactory(ImageDecoderDecoder.Factory())
            .build()
    )
    Image(
        painter = painter,
        contentDescription = "Local GIF",
        modifier = Modifier.size(200.dp)
    )
}