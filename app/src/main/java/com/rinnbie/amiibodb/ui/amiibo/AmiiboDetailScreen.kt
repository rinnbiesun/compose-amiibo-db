package com.rinnbie.amiibodb.ui.amiibo

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.rinnbie.amiibodb.R
import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.data.Release

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmiiboDetailRoute(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    viewModel: AmiiboDetailViewModel = hiltViewModel()
) {

    val amiiboDetailUiState: AmiiboDetailUiState by viewModel.amiiboDetailUiState.collectAsStateWithLifecycle()

    Column {
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(onClick = { onNavigateBack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        AmiiboDetailScreen(
            modifier = modifier,
            amiiboDetailUiState = amiiboDetailUiState
        )
    }
}

@Composable
internal fun AmiiboDetailScreen(
    modifier: Modifier,
    amiiboDetailUiState: AmiiboDetailUiState
) {
    Column(
        modifier = modifier
    ) {
        when (amiiboDetailUiState) {
            AmiiboDetailUiState.Loading -> {
                Text(text = "Detail Screen #")
            }
            AmiiboDetailUiState.Error -> {
                Text(text = "Detail Screen #")
            }
            is AmiiboDetailUiState.Success -> {
                AmiiboDetailBody(amiibo = amiiboDetailUiState.amiibo)
            }
        }
    }
}

@Composable
fun AmiiboDetailBody(
    modifier: Modifier = Modifier,
    amiibo: Amiibo,
    isPreview: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${amiibo.name}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "${amiibo.amiiboSeries}",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(24.dp))
            if (!isPreview) {
                AsyncImage(
                    modifier = modifier,
                    model = amiibo.image,
                    error = painterResource(id = R.drawable.no_image),
                    contentDescription = null
                )
            } else {
                Image(
                    modifier = Modifier.padding(10.dp),
                    painter = painterResource(id = R.drawable.testing_image),
                    contentDescription = null
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        ReleaseDateSection(amiibo)
    }
}

@Composable
private fun ReleaseDateSection(amiibo: Amiibo) {
    if (!amiibo.release?.au.isNullOrEmpty()) {
        ReleaseDateItem(R.drawable.logo_australia, "au", amiibo.release?.au)
        Spacer(modifier = Modifier.height(8.dp))
    }

    if (!amiibo.release?.eu.isNullOrEmpty()) {
        ReleaseDateItem(R.drawable.logo_europe, "eu", amiibo.release?.eu)
        Spacer(modifier = Modifier.height(8.dp))
    }

    if (!amiibo.release?.jp.isNullOrEmpty()) {
        ReleaseDateItem(R.drawable.logo_japan, "jp", amiibo.release?.jp)
        Spacer(modifier = Modifier.height(8.dp))
    }

    if (!amiibo.release?.na.isNullOrEmpty()) {
        ReleaseDateItem(R.drawable.logo_north_america, "na", amiibo.release?.na)
    }
}

@Composable
private fun ReleaseDateItem(
    @DrawableRes id: Int,
    contentDescription: String,
    releaseDate: String?
) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Image(
            modifier = Modifier
                .height(25.dp),
            painter = painterResource(id),
            contentDescription = contentDescription
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = releaseDate.orEmpty(),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AmiiboDetailScreenPreview() {
    val release = Release("2014-11-29", "2014-11-29", "2014-11-29", "2014-11-29")
    val amiibo = Amiibo(
        id = "1",
        name = "カズヤ",
        amiiboSeries = "大乱闘スマッシュブラザーズシリーズ",
        release = release
    )
    Column {
        CenterAlignedTopAppBar(
            title = { Text(text = amiibo.name.orEmpty()) },
            navigationIcon = {
                IconButton(onClick = { }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        AmiiboDetailBody(
            amiibo = amiibo,
            isPreview = true
        )
    }
}

