package com.rinnbie.amiibodb.ui.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rinnbie.amiibodb.R
import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.ui.HomeUiState
import com.rinnbie.amiibodb.ui.MainViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val homeState: HomeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()

    val contentPadding = WindowInsets
        .systemBars
        .add(WindowInsets(left = 16.dp, top = 16.dp, right = 16.dp, bottom = 16.dp))
        .asPaddingValues()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HomeHeader()
        }
    }

    when (val uiState = homeState) {
        HomeUiState.Loading -> {

        }
        HomeUiState.Error -> {

        }
        is HomeUiState.Success -> {
            AmiiboList(uiState.amiiboList)
        }
    }
}

@Composable
private fun HomeHeader() {
    Text(
        text = stringResource(id = R.string.app_name),
        style = MaterialTheme.typography.h1
    )
}

@Composable
private fun AmiiboList(list: List<Amiibo>) {
    Log.d("HomeScreen", "list size = ${list.size}")
}