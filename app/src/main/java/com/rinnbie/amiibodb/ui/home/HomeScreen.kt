package com.rinnbie.amiibodb.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.rinnbie.amiibodb.R
import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.Release
import com.rinnbie.amiibodb.data.Series
import com.rinnbie.amiibodb.ui.HomeUiState
import com.rinnbie.amiibodb.ui.MainViewModel
import com.rinnbie.amiibodb.ui.components.AmiiboDBBackground
import com.rinnbie.amiibodb.ui.theme.AmiiboDBTheme
import com.rinnbie.amiibodb.ui.theme.Shapes


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier, viewModel: MainViewModel = hiltViewModel()
) {
    val homeState: HomeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()

    when (val uiState = homeState) {
        HomeUiState.Loading -> {
            HomeEmptyScreen()
        }
        HomeUiState.Error -> {
            HomeEmptyScreen()
        }
        is HomeUiState.Success -> {
            Log.d("HomeScreen", "homeState = ${uiState.homeData}")
            HomeContentScreen(uiState.homeData.series)
        }
    }
}

@Composable
private fun HomeEmptyScreen() {
    AmiiboDBTheme {
        AmiiboDBBackground {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    HomeHeader()
                }
            }
        }
    }
}

@Composable
private fun HomeContentScreen(series: List<Series> = emptyList()) {
    AmiiboDBTheme {
        AmiiboDBBackground {
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    HomeHeader()
                }
                item {
                    HomeSearchBar()
                }
                item {
                    HomeFullWidthButton(text = stringResource(id = R.string.all))
                }
                item {
                    Box(modifier = Modifier.height(16.dp))
                }
                if (series.isEmpty()) {
                    item {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            for (i in 0 until 3) {
                                HomeCircleButton(
                                    series = Series(
                                        "0x00", "Super Smash Bros.", Amiibo(
                                            "",
                                            "",
                                            "",
                                            "",
                                            "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00000002.png",
                                            "",
                                            Release("", "", "", ""),
                                            "",
                                            ""
                                        )
                                    )
                                )
                            }
                        }
                    }
                    item {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            HomeCircleButton(
                                series = Series(
                                    "0x00", "Super Smash Bros.", Amiibo(
                                        "",
                                        "",
                                        "",
                                        "",
                                        "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_00000000-00000002.png",
                                        "",
                                        Release("", "", "", ""),
                                        "",
                                        ""
                                    )
                                )
                            )
                        }
                    }
                } else {
                    val cols = 3
                    items(series.chunked(cols)) { items ->
                        Row {
                            for ((index, item) in items.withIndex()) {
                                HomeCircleButton(
                                    modifier = Modifier.fillMaxWidth(1f / (cols - index)),
                                    series = item
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeHeader() {
    Column(Modifier.padding(top = 16.dp)) {
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleLarge
        )
        Box(Modifier.height(8.dp))
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val image: Painter = painterResource(id = R.drawable.logo_amiibo)
            Image(painter = image, contentDescription = "")
        }
    }
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
private fun HomeCircleButton(modifier: Modifier = Modifier, series: Series) {
    Column(
        modifier = modifier
            .height(120.dp)
            .padding(bottom = 48.dp)
            .aspectRatio(1f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1.0f)
                .weight(1f)
            /*.background(
                 MaterialTheme.colorScheme.secondaryContainer, shape = CircleShape
             )*/,
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = series.defaultAmiibo?.image, contentDescription = null
            )
        }
        Box(modifier = Modifier.height(5.dp))
        Box {
            Text(
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center,
                text = series.name,
                maxLines = 1,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
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
                MaterialTheme.colorScheme.secondaryContainer, shape = Shapes.medium
            ), contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.headlineLarge)
    }
}


@Preview(showBackground = true)
@Composable
fun HomeEmptyScreenPreview() {
    AmiiboDBTheme {
        HomeEmptyScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AmiiboDBTheme {
        HomeContentScreen()
    }
}