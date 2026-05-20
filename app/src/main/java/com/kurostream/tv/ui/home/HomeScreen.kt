package com.kurostream.tv.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.kurostream.tv.domain.model.Anime

val FAKE_ANIMES = listOf(
    Anime("1", "Solo Leveling", 12, 1, 1, "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx151807-m1gX3iqITiv9.png", 8.5f, "FINISHED"),
    Anime("2", "Jujutsu Kaisen", 24, 2, 2, "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx112059-m5sCBpwX0uEE.png", 9.0f, "FINISHED"),
    Anime("3", "Demon Slayer", 26, 3, 3, "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx101922-PEn1CTc93DQl.jpg", 8.8f, "FINISHED"),
    Anime("4", "Attack on Titan", 25, 4, 4, "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx16498-73IhOXpJZiMF.jpg", 9.5f, "FINISHED"),
    Anime("5", "One Piece", 1000, 5, 5, "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/nx21-tXMN3Y20PIL9.jpg", 9.2f, "RELEASING")
)

@Composable
fun HomeScreen() {
    var focusedAnime by remember { mutableStateOf(FAKE_ANIMES.first()) }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0F0F0F))) {
        // Immersive Background
        Crossfade(
            targetState = focusedAnime.posterUrl,
            animationSpec = tween(700),
            modifier = Modifier.fillMaxSize()
        ) { url ->
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    alpha = 0.5f
                )
                // Gradient Overlay to blend with UI
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF0F0F0F),
                                    Color(0x800F0F0F),
                                    Color.Transparent
                                )
                            )
                        )
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0x800F0F0F),
                                    Color(0xFF0F0F0F)
                                )
                            )
                        )
                )
            }
        }

        // Main Content
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(60.dp))
            
            // Hero details
            Column(modifier = Modifier.padding(start = 56.dp, end = 56.dp).weight(1f)) {
                Text(
                    text = focusedAnime.title,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "${focusedAnime.status} • Rating: ${focusedAnime.rating} • ${focusedAnime.episodes} Episodes",
                    fontSize = 16.sp,
                    color = Color(0xFFAAAAAA)
                )
            }

            // Carousel Rows
            TvLazyColumn(
                contentPadding = PaddingValues(bottom = 48.dp, top = 24.dp),
                modifier = Modifier.weight(1.5f)
            ) {
                item {
                    CatalogRow(
                        title = "Trending Now",
                        animes = FAKE_ANIMES,
                        onAnimeFocused = { focusedAnime = it }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    CatalogRow(
                        title = "Because you watched Solo Leveling",
                        animes = FAKE_ANIMES.shuffled(),
                        onAnimeFocused = { focusedAnime = it }
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                    CatalogRow(
                        title = "Top Rated",
                        animes = FAKE_ANIMES.shuffled(),
                        onAnimeFocused = { focusedAnime = it }
                    )
                }
            }
        }
    }
}

@Composable
fun CatalogRow(title: String, animes: List<Anime>, onAnimeFocused: (Anime) -> Unit) {
    Column {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            modifier = Modifier.padding(start = 56.dp, bottom = 16.dp)
        )
        TvLazyRow(
            contentPadding = PaddingValues(horizontal = 56.dp)
        ) {
            items(animes) { anime ->
                AnimeCard(anime = anime, onFocused = { onAnimeFocused(anime) })
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

@Composable
fun AnimeCard(anime: Anime, onFocused: () -> Unit) {
    Card(
        onClick = { /* TODO: Navigate to detail */ },
        modifier = Modifier
            .width(140.dp)
            .aspectRatio(2f / 3f)
            .androidx.compose.ui.focus.onFocusChanged { state ->
                if (state.isFocused) {
                    onFocused()
                }
            },
        colors = CardDefaults.colors(
            containerColor = Color(0xFF1E1E1E),
            focusedContainerColor = Color(0xFF1E1E1E)
        ),
        shape = CardDefaults.shape(
            shape = RoundedCornerShape(8.dp)
        ),
        scale = CardDefaults.scale(
            focusedScale = 1.1f
        ),
        border = CardDefaults.border(
            focusedBorder = androidx.tv.foundation.Border(
                border = androidx.compose.foundation.BorderStroke(3.dp, Color(0xFFA66DFF)), // Custom TV accent
                shape = RoundedCornerShape(8.dp)
            )
        )
    ) {
        AsyncImage(
            model = anime.posterUrl,
            contentDescription = anime.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp))
        )
    }
}
