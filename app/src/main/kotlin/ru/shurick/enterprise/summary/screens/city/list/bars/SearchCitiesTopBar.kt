package ru.shurick.enterprise.summary.screens.city.list.bars

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.shurick.enterprise.summary.R
import ru.shurick.enterprise.summary.screens.city.list.ListCitiesViewModel
import ru.shurick.enterprise.summary.theme.Shapes

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchCitiesTopBar(viewModel: ListCitiesViewModel, close: () -> Unit) {

    val searchQueryString by viewModel.searchQueryString.collectAsState()

    TopAppBar(
        contentPadding = PaddingValues(0.dp),
    ) {
        TextField(

            value = searchQueryString,
            onValueChange = { viewModel.searchQueryString.value = it },
            modifier = Modifier.fillMaxWidth(),
            shape = Shapes.medium,
            label = { Text(text = stringResource(R.string.search_cities_hint_text)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { viewModel.onSearchClick() }),
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = null)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = Color.Gray,
                focusedBorderColor = Color.Gray,
                focusedLabelColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color.Gray,
                leadingIconColor = Color.Gray,
                trailingIconColor = Color.Gray
            ),
            textStyle = TextStyle(fontSize = 14.sp),
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (searchQueryString.isNotBlank()) {
                            viewModel.searchQueryString.value = ""
                        } else {
                            close()
                        }
                    }
                ) {
                    Icon(Icons.Filled.Close, contentDescription = null)
                }
            }
        )
    }
}