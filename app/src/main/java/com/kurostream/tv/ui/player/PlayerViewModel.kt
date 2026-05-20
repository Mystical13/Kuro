package com.kurostream.tv.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurostream.tv.domain.model.StreamSource
import com.kurostream.tv.domain.provider.ProviderAggregator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val providerAggregator: ProviderAggregator
) : ViewModel() {

    private val _streams = MutableStateFlow<List<StreamSource>>(emptyList())
    val streams: StateFlow<List<StreamSource>> = _streams.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun loadStreams(animeId: String, episodeNumber: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _streams.value = providerAggregator.getStreams(animeId, episodeNumber)
            _isLoading.value = false
        }
    }
}
