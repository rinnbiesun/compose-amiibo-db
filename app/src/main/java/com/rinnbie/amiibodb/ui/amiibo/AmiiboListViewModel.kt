package com.rinnbie.amiibodb.ui.amiibo

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
class AmiiboListViewModel @Inject constructor(
    amiiboRepository: AmiiboRepository
) : ViewModel() {

    val amiiboListUiState: StateFlow<AmiiboListUiState> =
        amiiboListStateStream(amiiboRepository)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AmiiboListUiState.Loading
            )


    private fun amiiboListStateStream(amiiboRepository: AmiiboRepository): Flow<AmiiboListUiState> {
        return amiiboRepository.getAllAmiibos(forceUpdate = false)
            .asResult()
            .map { listResult ->
                when (listResult) {
                    is Result.Success -> {
                        AmiiboListUiState.Success(
                            listResult.data
                        )
                    }
                    is Result.Loading -> AmiiboListUiState.Loading
                    is Result.Error -> AmiiboListUiState.Error
                }
            }
    }
}

sealed interface AmiiboListUiState {
    data class Success(val amiibos: List<Amiibo>) : AmiiboListUiState
    object Error : AmiiboListUiState
    object Loading : AmiiboListUiState
}