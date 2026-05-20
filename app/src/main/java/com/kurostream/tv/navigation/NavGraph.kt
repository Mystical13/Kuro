package com.kurostream.tv.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.Icon
import androidx.tv.material3.Text
import com.kurostream.tv.ui.home.HomeScreen
import com.kurostream.tv.ui.anilist.AniListTabScreen
import com.kurostream.tv.ui.detail.AnimeDetailScreen
import com.kurostream.tv.ui.player.PlayerScreen
import com.kurostream.tv.ui.search.SearchScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    var isRailFocused by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0F0F0F))) {
        // Main Content Area (Behind the rail, pushed by collapsed width)
        Box(modifier = Modifier.fillMaxSize().padding(start = 80.dp)) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen(
                        onAnimeClick = { anime ->
                            navController.navigate("detail/${anime.id}")
                        }
                    )
                }
                composable("discover") {
                    SearchScreen(
                        onAnimeClick = { anime ->
                            navController.navigate("detail/${anime.id}")
                        }
                    )
                }
                composable("my_list") {
                    AniListTabScreen(onSyncClick = {})
                }
                composable("settings") {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Settings", color = Color.White, fontSize = 24.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("App Version 1.0.0 (Clean Architecture)", color = Color.Gray)
                        }
                    }
                }
                composable("detail/{animeId}") { backStackEntry ->
                    val animeId = backStackEntry.arguments?.getString("animeId") ?: ""
                    AnimeDetailScreen(
                        animeId = animeId,
                        onPlayClicked = { episodeNumber ->
                            navController.navigate("player/$animeId/$episodeNumber")
                        }
                    )
                }
                composable("player/{animeId}/{episodeNumber}") { backStackEntry ->
                    val animeId = backStackEntry.arguments?.getString("animeId") ?: ""
                    val episodeNumber = backStackEntry.arguments?.getString("episodeNumber")?.toIntOrNull() ?: 1
                    PlayerScreen(
                        animeId = animeId,
                        episodeNumber = episodeNumber,
                        onNavigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
            }
        }

        // Overlay Navigation Rail (Nuvio Style)
        val railWidth by animateDpAsState(if (isRailFocused) 220.dp else 80.dp, label = "railWidth")
        val railBackground = if (isRailFocused) Color(0xEA000000) else Color.Transparent
        
        Column(
            modifier = Modifier
                .width(railWidth)
                .fillMaxHeight()
                .background(railBackground)
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TvNavigationItem(
                icon = Icons.Default.Home,
                text = "Home",
                isExpanded = isRailFocused,
                onFocus = { isRailFocused = true },
                onBlur = { isRailFocused = false }
            ) { navController.navigate("home") }
            
            TvNavigationItem(
                icon = Icons.Default.Search,
                text = "Discover",
                isExpanded = isRailFocused,
                onFocus = { isRailFocused = true },
                onBlur = { isRailFocused = false }
            ) { navController.navigate("discover") }
            
            TvNavigationItem(
                icon = Icons.Default.List,
                text = "My List",
                isExpanded = isRailFocused,
                onFocus = { isRailFocused = true },
                onBlur = { isRailFocused = false }
            ) { navController.navigate("my_list") }
            
            TvNavigationItem(
                icon = Icons.Default.Settings,
                text = "Settings",
                isExpanded = isRailFocused,
                onFocus = { isRailFocused = true },
                onBlur = { isRailFocused = false }
            ) { navController.navigate("settings") }
        }
    }
}

@Composable
fun TvNavigationItem(
    icon: ImageVector,
    text: String,
    isExpanded: Boolean,
    onFocus: () -> Unit,
    onBlur: () -> Unit,
    onClick: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isFocused) Color(0x33A66DFF) else Color.Transparent)
            .onFocusChanged { state ->
                isFocused = state.isFocused
                if (state.isFocused) onFocus() else onBlur()
            }
            .clickable(onClick = onClick)
            .focusable(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (isExpanded) Arrangement.Start else Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = if (isFocused) Color(0xFFA66DFF) else Color(0xFF7A7A7A),
            modifier = Modifier.padding(14.dp).size(28.dp)
        )
        AnimatedVisibility(visible = isExpanded) {
            Text(
                text = text,
                color = if (isFocused) Color.White else Color(0xFF7A7A7A),
                fontWeight = if (isFocused) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}
