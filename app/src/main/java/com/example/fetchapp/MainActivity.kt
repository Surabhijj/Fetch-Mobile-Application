package com.example.fetchapp

import ItemsViewModelFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import com.example.fetchapp.repository.ItemsRepositoryImplementation
import com.example.fetchapp.ui.AppScreen
import com.example.fetchapp.viewModel.ItemsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = ItemsRepositoryImplementation()
        val viewModelFactory = ItemsViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)
        setContent {
            val items by viewModel.items.observeAsState(initial = emptyList())
            AppScreen(viewModel = viewModel)
        }
    }
}


