package com.kurostream.tv.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.tv.material3.Text

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(16.dp)) {
            TvNavigationItem("Home") { navController.navigate("home") }
            TvNavigationItem("Discover") { navController.navigate("discover") }
            TvNavigationItem("MyList") { navController.navigate("my_list") }
            TvNavigationItem("Settings") { navController.navigate("settings") }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Home Screen", color = Color.White)
                    }
                }
                composable("discover") {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Discover Screen", color = Color.White)
                    }
                }
                composable("my_list") {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("My List Screen", color = Color.White)
                    }
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
fun TvNavigationItem(text: String, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Box(
        modifier = Modifier
            .padding(8.dp)
            .focusable(interactionSource = interactionSource)
            .background(if (isFocused) Color.White else Color.Transparent)
            .padding(16.dp)
    ) {
        Text(
            text = text,
            color = if (isFocused) Color.Black else Color.Gray
        )
    }
}
