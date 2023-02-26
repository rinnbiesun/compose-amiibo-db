package com.rinnbie.amiibodb.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rinnbie.amiibodb.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchTextField(
    query: String,
    onBackClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    onQueryChange: (String) -> Unit = {},
) {
    Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
        val focusRequester = remember { FocusRequester() }

        val textFieldValue = remember {
            mutableStateOf(
                TextFieldValue(
                    text = query,
                    selection = TextRange(query.length)
                )
            )
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        BasicTextField(
            value = textFieldValue.value,
            onValueChange = {
                val searchingText = it.text
                textFieldValue.value = TextFieldValue(
                    text = searchingText,
                    selection = TextRange(searchingText.length)
                )
                onQueryChange(searchingText)
            },
            modifier = Modifier
                .height(72.dp)
                .focusRequester(focusRequester)
                .onKeyEvent { keyEvent ->
                    if (keyEvent.type == KeyEventType.KeyUp && keyEvent.key == Key.Back
                    ) {
                        onBackClick()
                        return@onKeyEvent true
                    }
                    return@onKeyEvent false
                },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
            decorationBox = { innerTextField ->
                SearchTextFieldLayout(
                    onBackClick = onBackClick,
                    onClearClick = onClearClick,
                    textField = innerTextField
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )
    }
}

@Composable
fun SearchTextFieldLayout(
    onBackClick: () -> Unit,
    onClearClick: () -> Unit,
    textField: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = stringResource(id = R.string.search),
            modifier = Modifier
                .padding(start = 16.dp)
                .clickable {
                    onBackClick()
                },
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(width = 8.dp))
        Box(modifier = Modifier.weight(1f)) {
            textField()
        }
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = stringResource(id = R.string.clear),
            modifier = Modifier
                .padding(end = 16.dp)
                .clickable {
                    onClearClick()
                },
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchTextFieldPreview() {
    SearchTextField("ABC")
}