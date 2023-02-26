package com.rinnbie.amiibodb.ui.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.rinnbie.amiibodb.R
import com.rinnbie.amiibodb.data.Series
import com.rinnbie.amiibodb.model.HomeData
import com.rinnbie.amiibodb.ui.amiibo.AmiiboItem
import com.rinnbie.amiibodb.ui.search.SearchTextField
import com.rinnbie.amiibodb.ui.theme.AmiiboDBTheme
import com.rinnbie.amiibodb.ui.theme.Shapes


@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    onNavigateToList: (String) -> Unit,
    onAmiiboClick: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeState by viewModel.homeUiState.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        homeState = homeState,
        onNavigateToList = onNavigateToList,
        onTryAgainClick = {
            viewModel.refreshUiState()
        },
        onAmiiboClick = onAmiiboClick,
        onClearClick = viewModel::clearQuery,
        onQueryChange = viewModel::search
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    homeState: HomeUiState,
    onNavigateToList: (String) -> Unit,
    onTryAgainClick: () -> Unit = {},
    onAmiiboClick: (String) -> Unit,
    onClearClick: () -> Unit = {},
    onQueryChange: (String) -> Unit = {},
) {
    when (homeState) {
        HomeUiState.Loading -> {
            Log.d("HomeScreen", "HomeUiState.Loading")
            HomeLoadingScreen()
        }
        HomeUiState.Error -> {
            Log.d("HomeScreen", "HomeUiState.Error")
            HomeErrorScreen(
                onTryAgainClick = onTryAgainClick
            )
        }
        is HomeUiState.Success -> {
            Log.d("HomeScreen", "HomeUiState.Success")
            HomeContentScreen(
                modifier = modifier,
                homeState = homeState,
                onNavigateToList = onNavigateToList,
                onAmiiboClick = onAmiiboClick,
                onClearClick = onClearClick,
                onQueryChange = onQueryChange
            )
        }
    }
}

@Composable
private fun HomeLoadingScreen() {
    HomeBody() {
        item {
            for (i in 0 until 12) {
                Row {
                    for (i in 0 until 3) {
                        SeriesPlaceholder(modifier = Modifier.fillMaxWidth(1f / (3 - i)))
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeContentScreen(
    modifier: Modifier = Modifier,
    homeState: HomeUiState.Success,
    onNavigateToList: (String) -> Unit = {},
    onAmiiboClick: (String) -> Unit = {},
    onClearClick: () -> Unit = {},
    onQueryChange: (String) -> Unit = {},
) {
    HomeBody(
        onNavigateToList = onNavigateToList,
        onAmiiboClick = onAmiiboClick,
        onClearClick = onClearClick,
        onQueryChange = onQueryChange,
    ) {
        val homeData = homeState.homeData
        if (homeData.series.isNotEmpty()) {
            val cols = 3
            items(homeData.series.chunked(cols)) { items ->
                Row {
                    for ((index, item) in items.withIndex()) {
                        SeriesButton(
                            modifier = modifier.fillMaxWidth(1f / (cols - index)), series = item,
                            onNavigateToList = onNavigateToList
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeErrorScreen(
    onTryAgainClick: () -> Unit = {},
) {
    Box {
        HomeAppBar()
        HomeHeader()
        ErrorContent(onTryAgainClick = onTryAgainClick)
    }
}

@Composable
fun ErrorContent(
    onTryAgainClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.error_screen),
            contentDescription = stringResource(
                id = R.string.error_screen
            )
        )
        Text(
            text = stringResource(id = R.string.error_message),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onTryAgainClick) {
            Text(
                text = stringResource(id = R.string.try_again),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun HomeBody(
    modifier: Modifier = Modifier,
    onNavigateToList: (String) -> Unit = {},
    onAmiiboClick: (String) -> Unit = {},
    onClearClick: () -> Unit = {},
    onQueryChange: (String) -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
    content: LazyListScope.() -> Unit
) {
    val query by viewModel.query.collectAsStateWithLifecycle()
    val searchResultState by viewModel.searchResultState.collectAsStateWithLifecycle()

    var isSearchMode by rememberSaveable { mutableStateOf(false) }
    val offset by animateFloatAsState(targetValue = if (isSearchMode) 0f else 1f)

    Column {
        AnimatedVisibility(visible = !isSearchMode) {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        }
        LazyColumn(
            modifier = modifier
                .padding(horizontal = 16.dp * offset)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp * offset),
            contentPadding = PaddingValues(bottom = 48.dp * offset)
        ) {
            if (!isSearchMode) {
                item {
                    HomeHeader()
                }
                item {
                    HomeSearchBar(onSearchBarClick = {
                        onClearClick()
                        isSearchMode = true
                    })
                }

                item {
                    AllAmiiboButton(
                        text = stringResource(id = R.string.all),
                        onNavigateToList = onNavigateToList
                    )
                }
                content()
            } else {
                stickyHeader {
                    SearchTextField(
                        query = query,
                        onBackClick = {
                            isSearchMode = false
                        },
                        onClearClick = onClearClick,
                        onQueryChange = onQueryChange
                    )
                }
                val cols = 3
                items(searchResultState.chunked(3)) { items ->
                    Row(
                        modifier = modifier.padding(horizontal = 8.dp),
                    ) {
                        for ((index, item) in items.withIndex()) {
                            AmiiboItem(
                                modifier = modifier
                                    .fillMaxWidth(1f / (cols - index))
                                    .padding(horizontal = 8.dp, vertical = 8.dp),
                                amiibo = item,
                                onAmiiboClick = onAmiiboClick
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeAppBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        }
    )
}

@Composable
private fun HomeHeader() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        val image: Painter = painterResource(id = R.drawable.logo_amiibo)
        Image(modifier = Modifier.fillMaxSize(0.4f), painter = image, contentDescription = "")
    }
}

@Composable
private fun HomeSearchBar(
    onSearchBarClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
            .clickable { onSearchBarClick() },
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
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SeriesPlaceholder(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .height(120.dp)
            .padding(horizontal = 8.dp)
            .aspectRatio(1f)
            .placeholder(
                visible = true,
                color = Color.Gray.copy(0.2f),
                shape = Shapes.small,
                highlight = PlaceholderHighlight.shimmer(highlightColor = Color.White)
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {}
}

@Composable
private fun SeriesButton(
    modifier: Modifier = Modifier,
    series: Series,
    onNavigateToList: (String) -> Unit
) {
    Column(
        modifier = modifier
            .height(120.dp)
            .padding(horizontal = 8.dp)
            .aspectRatio(1f)
            .clickable { onNavigateToList(series.name) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1.0f)
                .weight(1f)
            /*.background(
                 MaterialTheme.colorScheme.secondaryContainer, shape = CircleShape
             )*/, contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                model = series.defaultAmiibo?.image,
                error = {
                    Text(text = "no network", textAlign = TextAlign.Center)
                },
                contentDescription = null,
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
private fun AllAmiiboButton(
    text: String = "",
    onNavigateToList: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)
            .background(
                MaterialTheme.colorScheme.secondaryContainer, shape = Shapes.medium
            )
            .clickable { onNavigateToList("all") }, contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.headlineLarge)
    }
}


@Preview(showBackground = true)
@Composable
fun HomeContentPreview() {
    AmiiboDBTheme {
        HomeContentScreen(homeState = HomeUiState.Success(HomeData()))
    }
}

@Preview(showBackground = true)
@Composable
fun HomeLoadingPreview() {
    AmiiboDBTheme {
        HomeLoadingScreen()
    }
}

@Preview(showBackground = true, heightDp = 1000)
@Composable
fun HomeErrorPreview() {
    AmiiboDBTheme {
        HomeErrorScreen()
    }
}