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
                val response = stremioAdapter.getTrending()
                if (response.isNotEmpty()) {
                    _trendingAnimes.value = response
                } else {
                    _trendingAnimes.value = getFallbackAnimes()
                }
            } catch (e: Exception) {
                _trendingAnimes.value = getFallbackAnimes()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getFallbackAnimes(): List<Anime> {
        return listOf(
            Anime(
                id = "kitsu:151807",
                title = "Solo Leveling",
                episodes = 12,
                year = 2024,
                season = 1,
                posterUrl = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx151807-m1gX3iqITiv9.png",
                backgroundUrl = "https://images.alphacoders.com/134/1344606.jpeg",
                description = "In a world where hunters must battle deadly monsters to protect mankind, Sung Jinwoo, a notoriously weak hunter, finds himself in a struggle for survival in an ultra-deadly double dungeon.",
                rating = 8.7f,
                status = "FINISHED"
            ),
            Anime(
                id = "kitsu:112059",
                title = "Jujutsu Kaisen",
                episodes = 24,
                year = 2020,
                season = 1,
                posterUrl = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx112059-m5sCBpwX0uEE.png",
                backgroundUrl = "https://images.alphacoders.com/112/1129202.png",
                description = "To cure a curse Yuji Itadori swallows a cursed finger of the demon Ryomen Sukuna and enters Jujutsu High School to train as a sorcerer.",
                rating = 9.0f,
                status = "FINISHED"
            ),
            Anime(
                id = "kitsu:101922",
                title = "Demon Slayer",
                episodes = 26,
                year = 2019,
                season = 1,
                posterUrl = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx101922-PEn1CTc93DQl.jpg",
                backgroundUrl = "https://images.alphacoders.com/100/1004381.jpg",
                description = "Tanjiro Kamado sets out on a perilous path to become a demon slayer and find a cure for his sister Nezuko, who has been turned into a demon.",
                rating = 8.9f,
                status = "FINISHED"
            ),
            Anime(
                id = "kitsu:16498",
                title = "Attack on Titan",
                episodes = 25,
                year = 2013,
                season = 1,
                posterUrl = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/bx16498-73IhOXpJZiMF.jpg",
                backgroundUrl = "https://images.alphacoders.com/830/830234.jpg",
                description = "Humankind lives in fear of massive man-eating titans behind gargantuan protective walls, until a colossal titan breaks through their defense.",
                rating = 9.5f,
                status = "FINISHED"
            ),
            Anime(
                id = "kitsu:21",
                title = "One Piece",
                episodes = 1000,
                year = 1999,
                season = 1,
                posterUrl = "https://s4.anilist.co/file/anilistcdn/media/anime/cover/large/nx21-tXMN3Y20PIL9.jpg",
                backgroundUrl = "https://images.alphacoders.com/131/1310162.jpeg",
                description = "Monkey D. Luffy assembles a loyal crew of pirates to search for the legendary hidden treasure, the 'One Piece', and become the King of the Pirates.",
                rating = 9.2f,
                status = "RELEASING"
            )
        )
    }
}
