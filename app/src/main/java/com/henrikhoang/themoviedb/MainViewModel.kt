package com.henrikhoang.themoviedb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    var currentPage: Int = INITIAL_PAGE
    private var searchDebounceJob: Job? = null

    private fun updateState(block: (UiState) -> UiState) {
        _uiState.update(block)
    }

    fun updateQuery(query: String, refresh: Boolean) {
        searchDebounceJob?.cancel()
        searchDebounceJob = viewModelScope.launch {
            delay(300L)
            getMovies(query, refresh)
        }
    }

    private fun getMovies(query: String = "", refresh: Boolean) {
        currentPage = if (refresh) INITIAL_PAGE else currentPage++
        viewModelScope.launch {
            runSuspendCatching {
                repository.queryMovie(query = query, page = currentPage, type = TYPE)
            }.onSuccess {
                if (refresh) {
                    updateState { uiState -> uiState.copy(movies = it.search) }
                } else {
                    uiState.value.movies?.toMutableList()?.addAll(it.search.orEmpty())
                    updateState { uiState -> uiState.copy(movies = uiState.movies) }
                }
            }.onFailure {
                updateState { uiState -> uiState.copy(movies = null, error = it) }
            }
        }
    }

    private inline fun <R> runSuspendCatching(block: () -> R): Result<R> {
        return try {
            Result.success(block())
        } catch(c: CancellationException) {
            throw c
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    companion object {
        const val TYPE = "movie"
        const val INITIAL_PAGE = 1
    }

}

data class UiState(
    val movies: List<MovieInfo>? = emptyList(),
    val error: Throwable? = null
)