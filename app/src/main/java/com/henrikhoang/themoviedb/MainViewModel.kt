package com.henrikhoang.themoviedb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var currentPage: Int = 1
    private var searchDebounceJob: Job? = null

    private var _uiState = MutableLiveData<UiState>()
    val uiState: LiveData<UiState> get() = _uiState

    fun updateQuery(query: String, refresh: Boolean) {
        currentPage = if (refresh) {
            1
        } else {
            currentPage + 1
        }
        searchDebounceJob?.cancel()
        searchDebounceJob = viewModelScope.launch {
            delay(300L)
            getMovies(query, refresh)
        }
    }

    private fun getMovies(query: String = "", refresh: Boolean) {
        viewModelScope.launch {
            runSuspendCatching {
                repository.queryMovie(query = query, page = currentPage, type = TYPE)
            }.onSuccess {
                if (refresh) {
                    _uiState.postValue(UiState(it.search.orEmpty(), null, true))
                } else {
                    val newList = uiState.value?.movies?.toMutableList()
                    newList?.addAll(it.search.orEmpty())
                    _uiState.postValue(UiState(newList, null, false))
                }
            }.onFailure {
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
    }

}

data class UiState(
    val movies: List<MovieInfo>? = emptyList(),
    val error: Throwable? = null,
    val refresh: Boolean = true
)