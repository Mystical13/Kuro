package com.kurostream.tv.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.Button
import androidx.tv.material3.Text
import androidx.tv.material3.Surface
import androidx.tv.material3.ClickableSurfaceDefaults

@Composable
fun AnimeDetailScreen(
    animeId: String,
    onPlayClicked: (episodeNumber: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0F0F))
            .padding(56.dp)
    ) {
        Text(
            text = "Anime Details",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "ID: $animeId",
            fontSize = 16.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onPlayClicked(1) },
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Text("Play Episode 1")
        }

        Text(
            text = "Provider Availability",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ProviderBadge("Stremio Addons")
            ProviderBadge("CloudStream Plugins")
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
