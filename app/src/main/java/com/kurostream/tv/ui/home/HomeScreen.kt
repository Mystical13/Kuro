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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.kurostream.tv.domain.model.Anime

val FAKE_ANIMES = listOf(
    Anime(id = "1", title = "Solo Leveling", episodes = 12, rating = 8.5f, status = "FINISHED", posterUrl = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx151807-m1gX3iqITiv9.png"),
    Anime(id = "2", title = "Jujutsu Kaisen", episodes = 24, rating = 9.0f, status = "FINISHED", posterUrl = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx112059-m5sCBpwX0uEE.png"),
    Anime(id = "3", title = "Demon Slayer", episodes = 26, rating = 8.8f, status = "FINISHED", posterUrl = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx101922-PEn1CTc93DQl.jpg"),
    Anime(id = "4", title = "Attack on Titan", episodes = 25, rating = 9.5f, status = "FINISHED", posterUrl = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx16498-73IhOXpJZiMF.jpg"),
    Anime(id = "5", title = "One Piece", episodes = 1000, rating = 9.2f, status = "RELEASING", posterUrl = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/nx21-tXMN3Y20PIL9.jpg")
)

@Composable
fun HomeScreen(
    onAnimeClick: (Anime) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val trendingAnimes by viewModel.trendingAnimes.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    var focusedAnime by remember { mutableStateOf<Anime?>(null) }

    // Set first anime as focused once data is loaded
    LaunchedEffect(trendingAnimes) {
        if (trendingAnimes.isNotEmpty() && focusedAnime == null) {
            focusedAnime = trendingAnimes.first()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0F0F0F))) {
        if (isLoading) {
            // Simple loading state
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Loading trending anime...", color = Color.White)
            }
            return@Box
        }

        // Immersive Background
        Crossfade(
            targetState = focusedAnime?.backgroundUrl ?: focusedAnime?.posterUrl,
            animationSpec = tween(700),
            modifier = Modifier.fillMaxSize(),
            label = "background_crossfade"
        ) { url ->
            if (url != null) {
                Box(modifier = Modifier.fillMaxSize()) {
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        alpha = 0.65f
                    )
                    // Nuvio-style heavy deep gradients for readability
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color(0xFF0F0F0F),
                                        Color(0xDD0F0F0F),
                                        Color(0x700F0F0F),
                                        Color.Transparent
                                    ),
                                    startX = 0f,
                                    endX = 1400f
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
                                        Color(0x700F0F0F),
                                        Color(0xFF0F0F0F)
                                    ),
                                    startY = 0f,
                                    endY = 1200f
                                )
                            )
                    )
                }
            }
        }

        val topRatedAnimes = remember(trendingAnimes) {
            trendingAnimes.sortedByDescending { it.rating }.take(10)
        }

        // Main Content
        if (focusedAnime != null) {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(90.dp))
                
                // Hero details
                Column(modifier = Modifier.padding(start = 56.dp, end = 120.dp).weight(1f)) {
                    Text(
                        text = focusedAnime!!.title,
                        fontSize = 58.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "${focusedAnime!!.year} • ${focusedAnime!!.status} • Rating: ${focusedAnime!!.rating}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFA66DFF) // App logo tint!
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = focusedAnime!!.description ?: "No description provided.",
                        fontSize = 16.sp,
                        color = Color(0xFFC0C0C0),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        lineHeight = 24.sp
                    )
                }

                // Carousel Rows
                TvLazyColumn(
                    contentPadding = PaddingValues(bottom = 48.dp, top = 24.dp),
                    modifier = Modifier.weight(1.5f)
                ) {
                    item {
                        CatalogRow(
                            title = "Trending on Kitsu",
                            animes = trendingAnimes,
                            onAnimeClick = onAnimeClick,
                            onAnimeFocused = { focusedAnime = it }
                        )
                    }
                    if (topRatedAnimes.size > 5) {
                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                            CatalogRow(
                                title = "Top Rated",
                                animes = topRatedAnimes,
                                onAnimeClick = onAnimeClick,
                                onAnimeFocused = { focusedAnime = it }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CatalogRow(
    title: String,
    animes: List<Anime>,
    onAnimeClick: (Anime) -> Unit,
    onAnimeFocused: (Anime) -> Unit
) {
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
                AnimeCard(
                    anime = anime,
                    onClick = { onAnimeClick(anime) },
                    onFocused = { onAnimeFocused(anime) }
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

@Composable
fun AnimeCard(
    anime: Anime,
    onClick: () -> Unit,
    onFocused: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .width(140.dp)
            .aspectRatio(2f / 3f)
            .onFocusChanged { state ->
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
