package com.hackbyte.recibot.recibot.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.hackbyte.recibot.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeCard(
    title: String,
    imageUrl: String,
    showPlaceHolder: Boolean = true,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "show recipe image",
            modifier = Modifier
                .placeholder(
                    visible = showPlaceHolder,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = MaterialTheme.colorScheme.background
                )
                .height(125.dp),
            error = painterResource(id = R.drawable.ic_baseline_image_search_24)
        )
        Text(
            text = title,
            modifier = Modifier
                .padding(16.dp)
                .placeholder(
                    visible = showPlaceHolder,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = MaterialTheme.colorScheme.background
                ),
            fontSize = 16.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.SemiBold
        )
    }

}