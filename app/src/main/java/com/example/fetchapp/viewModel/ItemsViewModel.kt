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
    private val _items = MutableLiveData<List<Item>?>()
    val items: MutableLiveData<List<Item>?> = _items

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _groupedItems = MutableLiveData<Map<Int, List<Item>>>()
    val groupedItems: LiveData<Map<Int, List<Item>>> = _groupedItems

    init {
        fetchItems()
        fetchAndGroupItems()
    }

    private fun fetchAndGroupItems() {
        viewModelScope.launch {
            val fetchedItems = repository.fetchItems()
            val filteredItems = fetchedItems?.filterNot { it.name.isNullOrEmpty() }
            val sortedFilterItems = filteredItems?.sortedBy { it.listId }
            val groupedAndSortedByListId = sortedFilterItems
                ?.groupBy { it.listId }
                ?.mapValues { entry ->
                    entry.value.sortedBy { it.id }
                }
            _groupedItems.postValue(groupedAndSortedByListId!!)
        }
    }

    private fun fetchItems() {
        viewModelScope.launch {
            _isLoading.value = true

            val fetchedItems = repository.fetchItems()
            val filteredItems = fetchedItems?.filterNot { it.name.isNullOrEmpty() }

            _items.postValue(filteredItems)
            _isLoading.value = false

        }
    }

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
            _items.postValue(sortedList)
            _isLoading.value = false

        }
    }
}
