package com.rinnbie.amiibodb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.repository.AmiiboRepository
import com.rinnbie.amiibodb.data.asResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.rinnbie.amiibodb.data.Result
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
            amiiboRepository.getAllAmiibo(),
            amiiboRepository.getAllSeries(),
            ::Pair
        ).asResult()
            .map { homeResult ->
                when (homeResult) {
                    is Result.Success -> {
                        val allAmiibo = homeResult.data.first.amiibo
                        val series = homeResult.data.second.series.onEach { series ->
                            series.defaultAmiibo =
                                allAmiibo.firstOrNull { series.name == it.amiiboSeries }
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