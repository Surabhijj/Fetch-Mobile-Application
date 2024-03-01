package com.example.fetchapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.fetchapp.constants.Constants
import com.example.fetchapp.model.Item
import com.example.fetchapp.viewModel.ItemsViewModel

@Composable
fun AppScreen(viewModel: ItemsViewModel) {
    val items by viewModel.items.observeAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Constants.APP_NAME) }, // the Header Name is assigned
                backgroundColor = Color.Blue,
                contentColor = Color.White,
            )
        }
    ) { innerPadding ->
        items?.let { BodyContent(items = it, Modifier.padding(innerPadding)) }
    }
}

@Composable
fun BodyContent(items: List<Item>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        ItemsScreen(items = items)
    }
}
