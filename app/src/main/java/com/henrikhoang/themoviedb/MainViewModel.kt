package com.henrikhoang.themoviedb

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class MainViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    var searchQuery: String = ""
    private var searchDebounceJob: Job? = null

    private fun updateState(block: (UiState) -> UiState) {
        _uiState.update(block)
    }

    fun getMovies(query: String = "", refresh: Boolean) {
        viewModelScope
    }

}

class UiState()