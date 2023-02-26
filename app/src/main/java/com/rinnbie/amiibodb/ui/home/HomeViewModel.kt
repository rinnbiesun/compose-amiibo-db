package com.rinnbie.amiibodb.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.rinnbie.amiibodb.data.Result
import com.rinnbie.amiibodb.data.Series
import com.rinnbie.amiibodb.data.source.AmiiboRepository
import com.rinnbie.amiibodb.model.HomeData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val amiiboRepository: AmiiboRepository
) : ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true))

    val homeUiState: StateFlow<HomeUiState> =
        viewModelState
            .map(HomeViewModelState::toUiState)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = viewModelState.value.toUiState()
            )

    val query = MutableStateFlow("")

    val searchResultState: StateFlow<List<Amiibo>> = query
        .debounce(500)
        .flatMapLatest {
            amiiboRepository.search(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )


    init {
        refreshUiState()
    }

    fun refreshUiState() {
        viewModelScope.launch {
            homeResultStream(amiiboRepository).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = true,
                                errorMessage = "",
                                amiibos = emptyList(),
                                series = emptyList()
                            )
                        }
                    }
                    is Result.Success -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "",
                                amiibos = result.data.amiibos,
                                series = result.data.series
                            )
                        }
                    }
                    is Result.Error -> {
                        viewModelState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.exception?.message ?: "refreshUiState error",
                                amiibos = emptyList(),
                                series = emptyList()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun homeResultStream(amiiboRepository: AmiiboRepository):
            Flow<Result<HomeData>> {
        return amiiboRepository.checkForceUpdate()
            .flatMapConcat { forceUpdate ->
                Log.d("HomeViewModel", "forceUpdate=$forceUpdate")
                combine(
                    amiiboRepository.getAllAmiibos(forceUpdate),
                    amiiboRepository.getAllSeries(forceUpdate),
                    ::HomeData
                ).asResult()
                    .onEach { result ->
                        if (result is Result.Success) {
                            val allAmiibo = result.data.amiibos
                            val series = result.data.series.onEach { series ->
                                series.defaultAmiibo =
                                    allAmiibo.firstOrNull { series.name == it.amiiboSeries }
                            }
                        }
                    }
            }.catch {
                Log.d("HomeViewModel", it.toString())
                emit(Result.Error(it))
            }
    }

    fun search(query: String) {
        viewModelScope.launch {
            this@HomeViewModel.query.emit(query)
        }
    }

    fun clearQuery() {
        query.tryEmit("")
    }
}

sealed interface HomeUiState {
    data class Success(val homeData: HomeData) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}

private data class HomeViewModelState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val amiibos: List<Amiibo> = emptyList(),
    val series: List<Series> = emptyList()
) {
    fun toUiState(): HomeUiState =
        if (errorMessage.isNotEmpty()) {
            HomeUiState.Error
        } else if (isLoading) {
            HomeUiState.Loading
        } else {
            HomeUiState.Success(HomeData(amiibos, series))
        }
}