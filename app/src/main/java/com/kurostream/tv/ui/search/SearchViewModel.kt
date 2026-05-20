package com.kurostream.tv.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kurostream.tv.domain.model.Anime
import com.kurostream.tv.domain.provider.ProviderAggregator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val providerAggregator: ProviderAggregator
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<Anime>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    fun search(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            _isSearching.value = true
            _searchResults.value = providerAggregator.search(query)
            _isSearching.value = false
        }
    }
}
