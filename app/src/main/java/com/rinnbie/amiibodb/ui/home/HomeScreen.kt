package com.rinnbie.amiibodb.ui.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.rinnbie.amiibodb.ui.theme.LocalBackgroundTheme
import com.rinnbie.amiibodb.ui.theme.Shapes


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    /*viewModel: MainViewModel = hiltViewModel(),*/
) {
    AmiiboDBTheme {
        AmiiboDBBackground {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    HomeHeader()
                }
                item {
                    Box(modifier = Modifier.height(48.dp))
                }
                item {
                    HomeFullWidthButton(text = stringResource(id = R.string.all))
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        HomeSquareButton()
                        HomeSquareButton()
                        HomeSquareButton()
                    }
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        HomeSquareButton()
                        HomeSquareButton()
                        HomeSquareButton()
                    }
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        HomeSquareButton()
                        HomeSquareButton()
                        HomeSquareButton()
                    }
                }
            }
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

@Composable
private fun HomeHeader() {
    Text(
        text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.titleLarge
    )
}


@Composable
private fun RowScope.HomeSquareButton() {
    Box(
        modifier = Modifier
            .aspectRatio(1.0f)
            .weight(1f)
            .background(
                LocalBackgroundTheme.current.colorScheme.secondaryContainer,
                shape = Shapes.medium
            )
    )
}

@Composable
private fun HomeFullWidthButton(
    text: String = ""
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)
            .background(
                LocalBackgroundTheme.current.colorScheme.secondaryContainer,
                shape = Shapes.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.headlineLarge)
    }
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