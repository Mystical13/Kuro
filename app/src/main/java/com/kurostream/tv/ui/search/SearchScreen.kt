package com.kurostream.tv.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.tv.foundation.lazy.grid.TvGridCells
import androidx.tv.foundation.lazy.grid.TvLazyVerticalGrid
import androidx.tv.foundation.lazy.grid.items
import androidx.tv.material3.Text
import com.kurostream.tv.domain.model.Anime
import com.kurostream.tv.ui.home.AnimeCard

@Composable
fun SearchScreen(
    onAnimeClick: (Anime) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val results by viewModel.searchResults.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0F0F))
            .padding(horizontal = 56.dp, vertical = 32.dp)
    ) {
        Text(
            text = "Search Anime",
            fontSize = 32.sp,
            color = Color.White,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            placeholder = { Text("Enter anime title...", color = Color.Gray) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF1E1E1E),
                unfocusedContainerColor = Color(0xFF161616),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color(0xFFA66DFF)
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                viewModel.search(searchQuery)
            }),
            singleLine = true
        )

        if (isSearching) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                Text("Searching...", color = Color(0xFFA66DFF))
            }
        } else if (results.isEmpty() && searchQuery.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                Text("No results found.", color = Color.Gray)
            }
        } else {
            TvLazyVerticalGrid(
                columns = TvGridCells.Fixed(5),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(results) { anime ->
                    AnimeCard(
                        anime = anime,
                        onClick = { onAnimeClick(anime) },
                        onFocused = {}
                    )
                }
            }
        }
    }
}
