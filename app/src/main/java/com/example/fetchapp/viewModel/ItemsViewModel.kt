package com.example.fetchapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchapp.constants.Constants
import com.example.fetchapp.model.Item
import com.example.fetchapp.repository.ItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemsViewModel(private val repository: ItemsRepository) : ViewModel() {
    // LiveData holding the list of items.
    private val _items = MutableLiveData<List<Item>?>()
    val items: MutableLiveData<List<Item>?> = _items

    // StateFlow to track loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    // LiveData for holding grouped items by their listId, facilitating grouped display in the UI.
    private val _groupedItems = MutableLiveData<Map<Int, List<Item>>>()
    val groupedItems: LiveData<Map<Int, List<Item>>> = _groupedItems

    init {
        // Initial data fetch operations.
        fetchItems()
        fetchAndGroupItems()
    }

    // Fetches and groups items by listId upon ViewModel initialization.
    private fun fetchAndGroupItems() {
        viewModelScope.launch {
            val fetchedItems = repository.fetchItems()
            val filteredItems = fetchedItems?.filterNot { it.name.isNullOrEmpty() }
            val sortedFilterItems = filteredItems?.sortedBy { it.listId }

            // Grouping items by listId and sorting each group by itemId.
            val groupedAndSortedByListId = sortedFilterItems
                ?.groupBy { it.listId }
                ?.mapValues { entry ->
                    entry.value.sortedBy { it.id }
                }
            _groupedItems.postValue(groupedAndSortedByListId!!)
        }
    }

    // Fetches items from the repository, filters out items with null or empty names, and updates the LiveData.
    private fun fetchItems() {
        viewModelScope.launch {
            _isLoading.value = true

            val fetchedItems = repository.fetchItems()
            val filteredItems = fetchedItems?.filterNot { it.name.isNullOrEmpty() }

            _items.postValue(filteredItems)
            _isLoading.value = false

        }
    }

    // Sorts items based on a given criteria
    fun sortItemsBy(sortCriteria: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val filteredList = _items.value?.filterNot { it.name.isNullOrEmpty() }
            val sortedList = when (sortCriteria) {
                Constants.LIST_ID -> filteredList?.sortedBy { it.listId }
                Constants.ID -> filteredList?.sortedBy { it.id }
                Constants.NAME -> filteredList?.sortedBy { it.name?.lowercase() }
                else -> filteredList
            }
            _items.postValue(sortedList) // Updates LiveData with sorted list.
            _isLoading.value = false

        }
    }
}
