package com.kurostream.tv.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.tv.material3.Button
import androidx.tv.material3.Text
import androidx.tv.material3.Surface
import androidx.tv.material3.ClickableSurfaceDefaults
import coil.compose.AsyncImage
import com.kurostream.tv.ui.home.FAKE_ANIMES // Temporary for mock data check

@Composable
fun AnimeDetailScreen(
    animeId: String,
    onPlayClicked: (episodeNumber: Int) -> Unit
) {
    // In a real app, we'd fetch this from a ViewModel
    val anime = remember(animeId) { FAKE_ANIMES.find { it.id == animeId } ?: FAKE_ANIMES.first() }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0F0F0F))) {
        // Background
        AsyncImage(
            model = anime.backgroundUrl ?: anime.posterUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            alpha = 0.4f
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xFF0F0F0F)),
                        startY = 0f,
                        endY = 1000f
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(56.dp)
        ) {
            Text(
                text = anime.title,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${anime.year} • ${anime.status} • Rating: ${anime.rating}",
                fontSize = 18.sp,
                color = Color(0xFFA66DFF)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = anime.description ?: "Discover the epic story of ${anime.title}.",
                fontSize = 16.sp,
                color = Color(0xFFC0C0C0),
                modifier = Modifier.width(600.dp),
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = { onPlayClicked(1) },
                    modifier = Modifier.width(200.dp)
                ) {
                    Text("Play Now")
                }
                
                Surface(
                    onClick = { /* Add to list */ },
                    colors = ClickableSurfaceDefaults.colors(
                        containerColor = Color(0xFF1E1E1E),
                        focusedContainerColor = Color(0xFF2A2A2A)
                    ),
                    shape = ClickableSurfaceDefaults.shape(RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = "+ Add to List",
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun ProviderBadge(name: String) {
    Surface(
        onClick = { /* TODO: provider config jump */ },
        shape = ClickableSurfaceDefaults.shape(
            shape = RoundedCornerShape(8.dp)
        ),
        colors = ClickableSurfaceDefaults.colors(
            containerColor = Color(0xFF1E1E1E),
            focusedContainerColor = Color(0xFFA66DFF)
        )
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = Color.White
        )
    }
}
