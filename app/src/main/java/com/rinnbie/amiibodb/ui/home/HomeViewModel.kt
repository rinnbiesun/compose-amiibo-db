package com.rinnbie.amiibodb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rinnbie.amiibodb.data.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.rinnbie.amiibodb.data.Result
import com.rinnbie.amiibodb.data.source.AmiiboRepository
import com.rinnbie.amiibodb.model.HomeData
import kotlinx.coroutines.flow.*

@HiltViewModel
class MainViewModel @Inject constructor(
    amiiboRepository: AmiiboRepository
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        homeUiStateStream(amiiboRepository = amiiboRepository)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HomeUiState.Loading
            )

    private fun homeUiStateStream(amiiboRepository: AmiiboRepository): Flow<HomeUiState> {
        return combine(
            amiiboRepository.getAllAmiibos(forceUpdate = true),
            amiiboRepository.getAllSeries(forceUpdate = true),
            ::Pair
        ).asResult()
            .map { homeResult ->
                when (homeResult) {
                    is Result.Success -> {
                        val allAmiibo = homeResult.data.first.onEach {
                            amiiboRepository.saveAmiibo(it)
                        }
                        val series = homeResult.data.second.onEach { series ->
                            series.defaultAmiibo =
                                allAmiibo.firstOrNull { series.name == it.amiiboSeries }
                            amiiboRepository.saveSeries(series)
                        }
                        HomeUiState.Success(
                            HomeData(
                                allAmiibo,
                                series
                            )
                        )
                    }
                    is Result.Loading -> HomeUiState.Loading
                    is Result.Error -> HomeUiState.Error
                }
            }
    }
}

sealed interface HomeUiState {
    data class Success(val homeData: HomeData) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}