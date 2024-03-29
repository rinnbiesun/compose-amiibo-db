package com.rinnbie.amiibodb.ui.amiibo

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.rinnbie.amiibodb.R
import com.rinnbie.amiibodb.data.Amiibo
import com.rinnbie.amiibodb.ui.theme.Shapes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AmiiboListRoute(
    modifier: Modifier = Modifier,
    title: String? = null,
    onNavigateBack: () -> Unit,
    onAmiiboClick: (String) -> Unit,
    viewModel: AmiiboListViewModel = hiltViewModel()
) {
    val amiiboListUiState: AmiiboListUiState by viewModel.amiiboListUiState.collectAsStateWithLifecycle()

    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(text = if (title.isNullOrEmpty()) stringResource(id = R.string.all) else title)
            },
            navigationIcon = {
                IconButton(onClick = { onNavigateBack() }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        AmiiboListScreen(
            modifier = modifier,
            amiiboListUiState = amiiboListUiState,
            onAmiiboClick = onAmiiboClick
        )
    }
}

@Composable
internal fun AmiiboListScreen(
    modifier: Modifier = Modifier,
    amiiboListUiState: AmiiboListUiState,
    onAmiiboClick: (String) -> Unit,
) {
    when (amiiboListUiState) {
        AmiiboListUiState.Loading -> {
            Log.d("AmiiboListScreen", "HomeUiState.Loading")
        }
        AmiiboListUiState.Error -> {
            Log.d("AmiiboListScreen", "HomeUiState.Error")
        }
        is AmiiboListUiState.Success -> {
            Log.d("AmiiboListScreen", "HomeUiState.Success")
            ListBody(
                modifier = modifier,
                amiibos = amiiboListUiState.amiibos,
                onAmiiboClick = onAmiiboClick
            )
        }
    }
}

@Composable
fun ListBody(
    modifier: Modifier = Modifier,
    amiibos: List<Amiibo>,
    onAmiiboClick: (String) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(
            count = amiibos.size
        ) { index ->
            AmiiboItem(modifier, amiibos[index], onAmiiboClick = onAmiiboClick)
        }
    }
}

@Composable
fun AmiiboItem(
    modifier: Modifier = Modifier,
    amiibo: Amiibo,
    onAmiiboClick: (String) -> Unit,
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader(context)

    val request = ImageRequest.Builder(context)
        .data(amiibo.image)
        .allowHardware(false)
        .build()

    val imagePainter = rememberAsyncImagePainter(
        model = request,
        imageLoader = imageLoader
    )

    var paletteColor by remember { mutableStateOf(Color.White.value.toInt()) }
    var bitmapImage by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(key1 = imagePainter) {
        launch {
            when (val result = imageLoader.execute(request)) {
                is SuccessResult -> {
                    val bitmap = (result.drawable as BitmapDrawable).bitmap
                    val vibrant = Palette.from(bitmap)
                        .generate()
                        .getVibrantColor(Color.White.value.toInt())
                    paletteColor = vibrant
                    bitmapImage = bitmap
                }
                is ErrorResult -> {

                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(color = Color(paletteColor), shape = Shapes.medium)
            .clickable { onAmiiboClick(amiibo.id) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        bitmapImage?.asImageBitmap()?.let { image ->
            Image(
                modifier = Modifier.padding(10.dp),
                bitmap = image,
                contentDescription = null
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val amiibo1 = Amiibo(id = "1", name = "Test 1")
    val amiibo2 = Amiibo(id = "2", name = "Test 2")
    val amiibo3 = Amiibo(id = "3", name = "Test 3")
    val amiibo4 = Amiibo(id = "4", name = "Test 4")
    ListBody(
        amiibos = listOf(amiibo1, amiibo2, amiibo3, amiibo4)
    )
}