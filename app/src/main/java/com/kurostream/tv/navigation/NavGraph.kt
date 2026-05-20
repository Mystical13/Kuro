package com.kurostream.tv.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.Icon
import androidx.tv.material3.Text
import com.kurostream.tv.ui.home.HomeScreen
import com.kurostream.tv.ui.anilist.AniListTabScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    var isRailFocused by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxSize().background(Color(0xFF0F0F0F))) {
        // Vertical Navigation Rail
        val railWidth by animateDpAsState(if (isRailFocused) 200.dp else 80.dp)
        
        Column(
            modifier = Modifier
                .width(railWidth)
                .fillMaxHeight()
                .background(Color(0xFF161616))
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

        // Main Content Area
        Box(modifier = Modifier.weight(1f).fillMaxHeight()) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    HomeScreen()
                }
                composable("discover") {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Discover Screen", color = Color.White)
                    }
                }
                composable("my_list") {
                    AniListTabScreen(onSyncClick = {})
                }
                composable("settings") {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Settings Screen", color = Color.White)
                    }
                }
            }
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
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isFocused) Color(0xFFA66DFF) else Color.Transparent)
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
            tint = if (isFocused) Color.White else Color.Gray,
            modifier = Modifier.padding(16.dp)
        )
        AnimatedVisibility(visible = isExpanded) {
            Text(
                text = text,
                color = if (isFocused) Color.White else Color.Gray,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}
