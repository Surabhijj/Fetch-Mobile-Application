package com.example.fetchapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fetchapp.constants.Constants
import com.example.fetchapp.model.Item
import com.example.fetchapp.viewModel.ItemsViewModel

@Composable
fun ItemsScreen(items: List<Item>?) {
    val viewModel: ItemsViewModel = viewModel()
    val items by viewModel.items.observeAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState()
    var showGroupedScreen by remember { mutableStateOf(false) }


    // State for managing dropdown visibility
    var showDropdown by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically) {
           Box{
               Button(
                   onClick = { showDropdown = true },
                   modifier = Modifier
                       .height(IntrinsicSize.Min)
                       .padding(8.dp)
               ) {
                   Icon(
                       imageVector = if (showDropdown) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                       contentDescription = "Toggle Dropdown"
                   )
                   Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                   Text("Sort By")
               }

               DropdownMenu(
                   expanded = showDropdown,
                   onDismissRequest = { showDropdown = false },
                   modifier = Modifier
                       .background(
                           MaterialTheme.colors.surface,
                           RoundedCornerShape(8.dp)
                       )
                       .padding(8.dp)

               ) {
                   DropdownMenuItem(onClick = {
                       showDropdown = false
                       showGroupedScreen = false

                       viewModel.sortItemsBy(Constants.LIST_ID)
                   }) {

                       Text(text = "sort by  List ID", modifier = Modifier.padding(start = 8.dp))
                   }
                   DropdownMenuItem(onClick = {
                       showDropdown = false
                       showGroupedScreen = false

                       viewModel.sortItemsBy(Constants.ID)

                   }) {

                       Text(text = "sort by ID", modifier = Modifier.padding(start = 8.dp))
                   }
                   DropdownMenuItem(onClick = {
                       showDropdown = false
                       showGroupedScreen = false // Add this line

                       viewModel.sortItemsBy(Constants.NAME)
                   }) {

                       Text(text = "Sort by Name", modifier = Modifier.padding(start = 8.dp))

                   }
               }
           }

            Spacer(modifier = Modifier.width(46.dp))

            Button(
                onClick = {
                    showGroupedScreen = !showGroupedScreen
                    showDropdown = false

                },
                modifier = Modifier
                    .height(IntrinsicSize.Min)

                    .padding(8.dp)
            ) {
                Text("Group By ListId")
            }
        }


        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterStart) {





            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }


        }
        if (showGroupedScreen) {
            GroupedItemsScreen()
        } else {
            ItemView()
        }


    }
}


@Composable
fun GroupedItemsScreen(viewModel: ItemsViewModel = viewModel()) {
    val groupedItems by viewModel.groupedItems.observeAsState(initial = emptyMap())

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        groupedItems.forEach { (listId, items) ->
            item {
                Text(
                    text = "List ID: $listId",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(8.dp)
                )
            }
            items(items) { item ->
                GroupByItemView(item)
            }
        }
    }
}

@Composable
fun GroupByItemView(item: Item){
    Text(
        text = "Name: ${item.name} - ID: ${item.id}",
        style = MaterialTheme.typography.body1,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    )
}

@Composable
fun ItemView(viewModel: ItemsViewModel = viewModel()) {

    val items by viewModel.items.observeAsState(initial = emptyList())
    LazyColumn {
        items(items ?: emptyList()) { item ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                elevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) { // Inner padding for content inside the card
                    Text(text = "List ID: ${item.listId}", style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.height(4.dp)) // Adds space between text components
                    Text(text = "ID: ${item.id}", style = MaterialTheme.typography.body1)
                    Spacer(modifier = Modifier.height(4.dp)) // Adds space between text components
                    Text(text = "Name: ${item.name}", style = MaterialTheme.typography.body1)
                }
            }
        }
    }

}

