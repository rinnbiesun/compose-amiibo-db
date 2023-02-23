package com.rinnbie.amiibodb.ui.search

import androidx.lifecycle.ViewModel
import com.rinnbie.amiibodb.data.source.AmiiboRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val amiiboRepository: AmiiboRepository
) : ViewModel() {



}