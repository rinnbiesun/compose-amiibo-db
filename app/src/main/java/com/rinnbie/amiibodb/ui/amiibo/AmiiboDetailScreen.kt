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

@Composable
fun AmiiboDetailRoute(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    viewModel: AmiiboDetailViewModel = hiltViewModel()
) {
    val amiiboDetailUiState: AmiiboDetailUiState by viewModel.amiiboDetailUiState.collectAsStateWithLifecycle()

    AmiiboDetailScreen(
        modifier = modifier,
        amiiboDetailUiState = amiiboDetailUiState,
        onNavigateBack = onNavigateBack
    )
}

@Composable
internal fun AmiiboDetailScreen(
    modifier: Modifier,
    amiiboDetailUiState: AmiiboDetailUiState,
    onNavigateBack: () -> Unit = {},
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
                AmiiboDetailBody(
                    amiibo = amiiboDetailUiState.amiibo,
                    onNavigateBack = onNavigateBack
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmiiboDetailBody(
    modifier: Modifier = Modifier,
    amiibo: Amiibo,
    onNavigateBack: () -> Unit = {},
    isPreview: Boolean = false,
) {
    Column {
        CenterAlignedTopAppBar(
            title = { Text(text = amiibo.name.orEmpty()) },
            navigationIcon = {
                IconButton(onClick = { onNavigateBack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
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
                    text = "${amiibo.amiiboSeries}",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(24.dp))
                if (!isPreview) {
                    AsyncImage(
                        modifier = modifier.height(200.dp),
                        model = amiibo.image,
                        error = painterResource(id = R.drawable.no_image),
                        contentDescription = null
                    )
                } else {
                    Image(
                        modifier = Modifier.height(200.dp),
                        painter = painterResource(id = R.drawable.testing_image),
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            ReleaseDateSection(amiibo)
            SeriesSection()
        }
    }
}

@Composable
private fun SeriesSection() {
    // TODO
}

@Composable
private fun ReleaseDateSection(amiibo: Amiibo) {
    val releaseDateObjList = arrayListOf(
        ReleaseDateObj(R.drawable.logo_australia, "au", amiibo.release?.au),
        ReleaseDateObj(R.drawable.logo_europe, "eu", amiibo.release?.eu),
        ReleaseDateObj(R.drawable.logo_japan, "jp", amiibo.release?.jp),
        ReleaseDateObj(R.drawable.logo_north_america, "na", amiibo.release?.na),
    )

    for ((index, releaseDateObj) in releaseDateObjList.withIndex()) {
        if (!releaseDateObj.releaseDate.isNullOrEmpty()) {
            ReleaseDateItem(
                releaseDateObj.id,
                releaseDateObj.contentDescription,
                releaseDateObj.releaseDate
            )
            if (index < releaseDateObjList.size - 1) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
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
    AmiiboDetailBody(
        amiibo = amiibo,
        isPreview = true
    )
}

data class ReleaseDateObj(
    @DrawableRes val id: Int,
    val contentDescription: String,
    val releaseDate: String?
)

