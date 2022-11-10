package com.rinnbie.amiibodb.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
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
import com.rinnbie.amiibodb.ui.components.AmiiboDBBackground
import com.rinnbie.amiibodb.ui.theme.AmiiboDBTheme
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
                    Row(modifier = Modifier.fillMaxWidth()) {
                        for (i in 0 until 5) {
                            AmiiboCard()
                        }
                    }
                }
                item {
                    HomeSearchBar()
                }
                item {
                    HomeFullWidthButton(text = stringResource(id = R.string.all))
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        for (i in 0 until 3) {
                            HomeSquareButton()
                        }
                    }
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        for (i in 0 until 3) {
                            HomeSquareButton()
                        }
                    }
                }
                item {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        for (i in 0 until 3) {
                            HomeSquareButton()
                        }
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
private fun HomeSearchBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
            .background(MaterialTheme.colorScheme.inverseOnSurface, CircleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = stringResource(id = R.string.search),
            modifier = Modifier.padding(start = 16.dp),
            tint = MaterialTheme.colorScheme.outline
        )
        Text(
            text = stringResource(id = R.string.search_hint),
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
private fun RowScope.AmiiboCard() {
    Box(
        modifier = Modifier
            .weight(1f)
            .height(80.dp),
        contentAlignment = Alignment.Center
    ) {
        val image: Painter = painterResource(id = R.drawable.amiibo_splatoon)
        Image(painter = image, contentDescription = "")
    }
}

@Composable
private fun RowScope.HomeSquareButton() {
    Box(
        modifier = Modifier
            .aspectRatio(1.0f)
            .weight(1f)
            .background(
                MaterialTheme.colorScheme.secondaryContainer,
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
                MaterialTheme.colorScheme.secondaryContainer,
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