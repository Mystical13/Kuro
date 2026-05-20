package com.kurostream.tv.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurostream.tv.data.adapter.stremio.StremioAdapter
import com.kurostream.tv.domain.model.Anime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val stremioAdapter: StremioAdapter
) : ViewModel() {

    private val _trendingAnimes = MutableStateFlow<List<Anime>>(emptyList())
    val trendingAnimes: StateFlow<List<Anime>> = _trendingAnimes.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        fetchTrending()
    }

    private fun fetchTrending() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Fetch trending from Stremio Kitsu Addon (usually just an empty search or specific catalog)
                val response = stremioAdapter.getTrending()
                _trendingAnimes.value = response
            } catch (e: Exception) {
                // Handle error
            } finally {
                _isLoading.value = false
            }
        }
    }
}
