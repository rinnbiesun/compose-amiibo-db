package com.rinnbie.amiibodb.ui.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rinnbie.amiibodb.R
import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.ui.HomeUiState
import com.rinnbie.amiibodb.ui.MainViewModel
import com.rinnbie.amiibodb.ui.theme.AmiiboDBBackground
import com.rinnbie.amiibodb.ui.theme.AmiiboDBTheme

@Composable
fun ColumnScope.SpaceFill() {
    Spacer(modifier = Modifier.weight(1f))
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    /*viewModel: MainViewModel = hiltViewModel(),*/
) {
    AmiiboDBBackground {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                HomeHeader()
            }
            item {
                Box(modifier = Modifier.height(48.dp))
            }
            item {
                HomeHeader()
            }
        }

        /*val homeState: HomeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()

        when (val uiState = homeState) {
            HomeUiState.Loading -> {

            }
            HomeUiState.Error -> {

            }
            is HomeUiState.Success -> {
                AmiiboList(uiState.amiiboList)
            }
        }*/
    }
}

@Composable
private fun HomeHeader() {
    Text(
        text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.titleLarge
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AmiiboDBTheme {
        HomeScreen()
    }
}

@Composable
private fun AmiiboList(list: List<Amiibo>) {
    Log.d("HomeScreen", "list size = ${list.size}")
}