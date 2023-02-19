package com.rinnbie.amiibodb.ui.amiibo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.Result
import com.rinnbie.amiibodb.data.asResult
import com.rinnbie.amiibodb.data.source.AmiiboRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AmiiboDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    amiiboRepository: AmiiboRepository
) : ViewModel() {

    private val id: String = checkNotNull(savedStateHandle["id"])

    val amiiboDetailUiState: StateFlow<AmiiboDetailUiState> =
        amiiboDetailStateStream(id, amiiboRepository)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AmiiboDetailUiState.Loading
            )

    private fun amiiboDetailStateStream(
        id: String,
        amiiboRepository: AmiiboRepository
    ): Flow<AmiiboDetailUiState> {
        return amiiboRepository.getAmiibo(id)
            .asResult()
            .map { result ->
                when (result) {
                    is Result.Success -> {
                        AmiiboDetailUiState.Success(
                            result.data
                        )
                    }
                    is Result.Loading -> AmiiboDetailUiState.Loading
                    is Result.Error -> AmiiboDetailUiState.Error
                }
            }
    }
}

sealed interface AmiiboDetailUiState {
    data class Success(val amiibo: Amiibo) : AmiiboDetailUiState
    object Error : AmiiboDetailUiState
    object Loading : AmiiboDetailUiState
}
